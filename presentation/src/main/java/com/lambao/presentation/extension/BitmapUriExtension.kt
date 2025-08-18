package com.lambao.presentation.extension

// Contents copied from old Bitmap+UriExtension with package adapted

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.annotation.WorkerThread
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

@WorkerThread
fun Bitmap.saveToInternalStorage(
    context: Context,
    fileName: String,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100
): Uri? {
    return try {
        val file = context.getInternalFile(fileName) ?: return null
        FileOutputStream(file).use { output ->
            compress(format, quality, output)
        }
        Uri.fromFile(file)
    } catch (e: IOException) {
        null
    }
}

@WorkerThread
fun Bitmap.saveToCache(
    context: Context,
    fileName: String,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100
): Uri? {
    return try {
        val file = context.getCacheFile(fileName) ?: return null
        FileOutputStream(file).use { output ->
            compress(format, quality, output)
        }
        Uri.fromFile(file)
    } catch (e: IOException) {
        null
    }
}

@WorkerThread
fun Bitmap.saveToMediaStore(
    context: Context,
    fileName: String,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100,
    relativePath: String = "Pictures/${context.getAppName()}"
): Uri? {
    return try {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !context.hasStoragePermission()) {
            return null
        }

        val contentResolver = context.contentResolver
        val mimeType = when (format) {
            Bitmap.CompressFormat.PNG -> "image/png"
            Bitmap.CompressFormat.JPEG -> "image/jpeg"
            Bitmap.CompressFormat.WEBP -> "image/webp"
            else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                when (format) {
                    Bitmap.CompressFormat.WEBP_LOSSY -> "image/webp"
                    Bitmap.CompressFormat.WEBP_LOSSLESS -> "image/webp"
                    else -> "image/jpeg"
                }
            } else "image/jpeg"
        }

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, relativePath)
                put(MediaStore.Images.Media.IS_PENDING, 1)
            } else {
                val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val file = File(directory, "$relativePath/$fileName")
                file.parentFile?.mkdirs()
                put(MediaStore.Images.Media.DATA, file.absolutePath)
            }
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            contentResolver.openOutputStream(it)?.use { output -> compress(format, quality, output) }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(it, contentValues, null, null)
            }
        }
        uri
    } catch (e: IOException) {
        null
    }
}

@WorkerThread
fun Uri.loadBitmap(
    context: Context,
    maxWidth: Int = 1024,
    maxHeight: Int = 1024,
    rotateIfNeeded: Boolean = true
): Bitmap? {
    return try {
        if (scheme == ContentResolver.SCHEME_CONTENT && !context.hasStoragePermission()) {
            return null
        }
        context.contentResolver.openInputStream(this)?.use { input ->
            val options = BitmapFactory.Options().apply {
                if (maxWidth > 0 && maxHeight > 0) {
                    inJustDecodeBounds = true
                    BitmapFactory.decodeStream(input, null, this)
                    inSampleSize = calculateInSampleSize(outWidth, outHeight, maxWidth, maxHeight)
                    inJustDecodeBounds = false
                }
            }
            context.contentResolver.openInputStream(this)?.use { newInput ->
                val bitmap = BitmapFactory.decodeStream(newInput, null, options) ?: return null
                if (rotateIfNeeded) {
                    val rotationDegrees = getRotationDegrees(context)
                    if (rotationDegrees != 0) {
                        val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
                        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                    }
                }
                bitmap
            }
        }
    } catch (e: IOException) { null }
}

@WorkerThread
fun Uri.loadBitmapFromUrl(
    context: Context,
    url: String,
    maxWidth: Int = 1024,
    maxHeight: Int = 1024,
    saveToCache: Boolean = false
): Bitmap? {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        connection.doInput = true
        connection.connect()
        if (connection.responseCode != HttpURLConnection.HTTP_OK) return null
        connection.inputStream.use { input ->
            val options = BitmapFactory.Options().apply {
                if (maxWidth > 0 && maxHeight > 0) {
                    inJustDecodeBounds = true
                    BitmapFactory.decodeStream(input, null, this)
                    inSampleSize = calculateInSampleSize(outWidth, outHeight, maxWidth, maxHeight)
                    inJustDecodeBounds = false
                }
            }
            val newConnection = URL(url).openConnection() as HttpURLConnection
            newConnection.inputStream.use { newInput ->
                val bitmap = BitmapFactory.decodeStream(newInput, null, options) ?: return null
                if (saveToCache) {
                    val fileName = url.substringAfterLast("/").takeIf { it.isNotEmpty() } ?: "${UUID.randomUUID()}.jpg"
                    bitmap.saveToCache(
                        context,
                        fileName,
                        getBitmapFormatForExtension(fileName.substringAfterLast("."))
                    )
                }
                bitmap
            }
        }
    } catch (e: IOException) { null }
}

@WorkerThread
fun Uri.saveBitmapFromUrlToMediaStore(
    context: Context,
    url: String,
    fileName: String,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100,
    relativePath: String = "Pictures/${context.getAppName()}"
): Uri? {
    val bitmap = loadBitmapFromUrl(context, url, saveToCache = false) ?: return null
    return bitmap.saveToMediaStore(context, fileName, format, quality, relativePath)
}

@WorkerThread
fun Uri.saveDocumentToMediaStore(
    context: Context,
    fileName: String,
    relativePath: String = "Documents/${context.getAppName()}"
): Uri? {
    return try {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !context.hasStoragePermission()) return null
        val mimeType = getMimeType(context) ?: return null
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, mimeType)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Downloads.RELATIVE_PATH, relativePath)
                put(MediaStore.Downloads.IS_PENDING, 1)
            } else {
                val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                val file = File(directory, "$relativePath/$fileName")
                file.parentFile?.mkdirs()
                put(MediaStore.Downloads.DATA, file.absolutePath)
            }
        }
        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            contentResolver.openOutputStream(it)?.use { output ->
                contentResolver.openInputStream(this)?.use { input -> input.copyTo(output) }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                contentResolver.update(it, contentValues, null, null)
            }
        }
        uri
    } catch (e: IOException) { null }
}

@WorkerThread
fun Uri.saveDocumentToInternalStorage(
    context: Context,
    fileName: String
): Uri? {
    return try {
        val file = context.getInternalFile(fileName) ?: return null
        context.contentResolver.openInputStream(this)?.use { input ->
            FileOutputStream(file).use { output -> input.copyTo(output) }
        }
        Uri.fromFile(file)
    } catch (e: IOException) { null }
}

@WorkerThread
fun Bitmap.saveToFile(
    file: File,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100
): Boolean {
    return try {
        file.parentFile?.mkdirs()
        FileOutputStream(file).use { output -> compress(format, quality, output) }
        true
    } catch (e: IOException) { false }
}

@WorkerThread
fun Uri.getFilePath(context: Context): String? {
    return try {
        when (scheme) {
            ContentResolver.SCHEME_FILE -> path
            ContentResolver.SCHEME_CONTENT -> {
                val projection = arrayOf(MediaStore.MediaColumns.DATA)
                context.contentResolver.query(this, projection, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                        if (columnIndex != -1) return cursor.getString(columnIndex)
                    }
                }
                null
            }
            else -> null
        }
    } catch (e: Exception) { null }
}

@WorkerThread
fun Uri.getFileSize(context: Context): Long {
    return try {
        when (scheme) {
            ContentResolver.SCHEME_CONTENT -> {
                context.contentResolver.query(this, arrayOf(OpenableColumns.SIZE), null, null, null)?.use {
                    if (it.moveToFirst()) {
                        val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
                        if (sizeIndex != -1) return it.getLong(sizeIndex)
                    }
                }
                context.contentResolver.openInputStream(this)?.use { input -> input.available().toLong() } ?: 0L
            }
            ContentResolver.SCHEME_FILE -> path?.let { File(it).length() } ?: 0L
            else -> 0L
        }
    } catch (e: Exception) { 0L }
}

@WorkerThread
fun Uri.getFileName(context: Context): String? {
    return try {
        when (scheme) {
            ContentResolver.SCHEME_FILE -> path?.let { File(it).name }
            ContentResolver.SCHEME_CONTENT -> {
                context.contentResolver.query(this, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use {
                    if (it.moveToFirst()) {
                        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (nameIndex != -1) return it.getString(nameIndex)
                    }
                }
                lastPathSegment
            }
            else -> lastPathSegment
        }
    } catch (e: Exception) { null }
}

fun Uri.getMimeType(context: Context): String? {
    return try {
        when (scheme) {
            ContentResolver.SCHEME_CONTENT -> context.contentResolver.getType(this)
            ContentResolver.SCHEME_FILE -> MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                MimeTypeMap.getFileExtensionFromUrl(toString())
            )
            else -> null
        }
    } catch (e: Exception) { null }
}

fun Bitmap.resize(width: Int, height: Int, filter: Boolean = true): Bitmap? = try { this.scale(width, height, filter) } catch (e: Exception) { null }

fun Bitmap.scaleToFit(maxWidth: Int, maxHeight: Int, filter: Boolean = true): Bitmap {
    if (width <= maxWidth && height <= maxHeight) return this
    val widthRatio = maxWidth.toFloat() / width
    val heightRatio = maxHeight.toFloat() / height
    val ratio = minOf(widthRatio, heightRatio)
    val newWidth = (width * ratio).toInt()
    val newHeight = (height * ratio).toInt()
    return this.scale(newWidth, newHeight, filter)
}

fun Bitmap.rotate(degrees: Float): Bitmap? = try {
    val matrix = Matrix().apply { postRotate(degrees) }
    Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
} catch (e: Exception) { null }

fun Bitmap.crop(x: Int, y: Int, width: Int, height: Int): Bitmap? = try {
    Bitmap.createBitmap(this, x, y, minOf(width, this.width - x), minOf(height, this.height - y))
} catch (e: Exception) { null }

fun Bitmap.toByteArray(
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100
): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(format, quality, stream)
    return stream.toByteArray()
}

@WorkerThread
fun Uri.copyTo(context: Context, target: Any, bufferSize: Int = 8192): Uri? {
    return try {
        val inputStream = context.contentResolver.openInputStream(this) ?: return null
        val outputStream: OutputStream = when (target) {
            is Uri -> context.contentResolver.openOutputStream(target) ?: return null
            is File -> { target.parentFile?.mkdirs(); FileOutputStream(target) }
            else -> return null
        }
        inputStream.use { input -> outputStream.use { output -> input.copyTo(output, bufferSize) } }
        when (target) { is Uri -> target; is File -> Uri.fromFile(target); else -> null }
    } catch (e: Exception) { null }
}

suspend fun Uri.loadBitmapSuspend(
    context: Context,
    maxWidth: Int = 1024,
    maxHeight: Int = 1024,
    rotateIfNeeded: Boolean = true
): Bitmap? = withContext(Dispatchers.IO) { loadBitmap(context, maxWidth, maxHeight, rotateIfNeeded) }

fun getBitmapFormatForExtension(extension: String): Bitmap.CompressFormat {
    return when (extension.lowercase()) {
        "png" -> Bitmap.CompressFormat.PNG
        "webp" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) Bitmap.CompressFormat.WEBP_LOSSY else @Suppress("DEPRECATION") Bitmap.CompressFormat.WEBP
        else -> Bitmap.CompressFormat.JPEG
    }
}

fun Uri.getBitmapFormat(context: Context): Bitmap.CompressFormat {
    val mimeType = getMimeType(context)?.lowercase()
    return when {
        mimeType?.contains("png") == true -> Bitmap.CompressFormat.PNG
        mimeType?.contains("webp") == true -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) Bitmap.CompressFormat.WEBP_LOSSY else @Suppress("DEPRECATION") Bitmap.CompressFormat.WEBP
        else -> Bitmap.CompressFormat.JPEG
    }
}

private fun calculateInSampleSize(width: Int, height: Int, reqWidth: Int, reqHeight: Int): Int {
    var inSampleSize = 1
    if (width > reqWidth || height > reqHeight) {
        val halfWidth = width / 2
        val halfHeight = height / 2
        while ((halfWidth / inSampleSize) >= reqWidth && (halfHeight / inSampleSize) >= reqHeight) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

private fun Uri.getRotationDegrees(context: Context): Int {
    return try {
        if (ContentResolver.SCHEME_FILE == scheme) {
            path?.let { filePath -> return getExifOrientation(filePath) }
        }
        context.contentResolver.openInputStream(this)?.use { input ->
            val exif = ExifInterface(input)
            return when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        }
        0
    } catch (e: Exception) { 0 }
}

private fun getExifOrientation(filePath: String): Int {
    return try {
        val exif = ExifInterface(filePath)
        when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
    } catch (e: Exception) { 0 }
}


