package com.lambao.presentation.extension

import android.content.Context
import androidx.annotation.WorkerThread
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

@WorkerThread
fun Context.getInternalFile(fileName: String, createParentDirs: Boolean = true): File? = try {
    File(filesDir, fileName).apply { if (createParentDirs) parentFile?.mkdirs() }
} catch (e: Exception) { null }

@WorkerThread
fun Context.getCacheFile(fileName: String, createParentDirs: Boolean = true): File? = try {
    File(cacheDir, fileName).apply { if (createParentDirs) parentFile?.mkdirs() }
} catch (e: Exception) { null }

@WorkerThread
fun Context.getInternalSubdirFile(subdir: String, fileName: String, createParentDirs: Boolean = true): File? = try {
    val subdirFile = File(filesDir, subdir).apply { if (createParentDirs) mkdirs() }
    File(subdirFile, fileName)
} catch (e: Exception) { null }

@WorkerThread
fun Context.getCacheSubdirFile(subdir: String, fileName: String, createParentDirs: Boolean = true): File? = try {
    val subdirFile = File(cacheDir, subdir).apply { if (createParentDirs) mkdirs() }
    File(subdirFile, fileName)
} catch (e: Exception) { null }

@WorkerThread
@Throws(SecurityException::class)
fun File.readText(charset: Charset = Charsets.UTF_8): String? = try {
    inputStream().bufferedReader(charset).use { it.readText() }
} catch (e: IOException) { null }

@WorkerThread
@Throws(SecurityException::class)
fun File.readLines(charset: Charset = Charsets.UTF_8): List<String> = try {
    bufferedReader(charset).useLines { it.toList() }
} catch (e: IOException) { emptyList() }

@WorkerThread
@Throws(SecurityException::class)
fun File.readBytes(): ByteArray? = try { FileInputStream(this).use { it.readBytes() } } catch (e: IOException) { null }

@WorkerThread
@Throws(SecurityException::class)
fun File.writeText(text: String, charset: Charset = Charsets.UTF_8, append: Boolean = false): Boolean {
    parentFile?.mkdirs()
    return try { FileOutputStream(this, append).use { it.write(text.toByteArray(charset)) }; true } catch (e: IOException) { false }
}

@WorkerThread
@Throws(SecurityException::class)
fun File.writeBytes(bytes: ByteArray, append: Boolean = false): Boolean {
    parentFile?.mkdirs()
    return try { FileOutputStream(this, append).use { it.write(bytes) }; true } catch (e: IOException) { false }
}

@WorkerThread
@Throws(SecurityException::class)
fun File.writeInputStream(inputStream: InputStream, append: Boolean = false, bufferSize: Int = 8192): Boolean {
    parentFile?.mkdirs()
    return try {
        FileOutputStream(this, append).use { output -> inputStream.use { it.copyTo(output, bufferSize) } }
        true
    } catch (e: IOException) { false }
}

@WorkerThread
@Throws(SecurityException::class)
fun File.copyTo(destination: File, overwrite: Boolean = false, bufferSize: Int = 8192): Boolean {
    if (!exists()) return false
    if (destination.exists() && !overwrite) return false
    return try {
        destination.parentFile?.mkdirs()
        FileInputStream(this).use { input -> FileOutputStream(destination).use { output -> input.copyTo(output, bufferSize) } }
        true
    } catch (e: IOException) { false }
}

@WorkerThread
@Throws(SecurityException::class)
fun File.moveTo(destination: File, overwrite: Boolean = false): Boolean {
    if (!exists()) return false
    if (destination.exists() && !overwrite) return false
    return try { if (copyTo(destination, overwrite)) delete() else false } catch (e: IOException) { false }
}

fun File.extension(): String {
    val name = name
    val dotIndex = name.lastIndexOf('.')
    return if (dotIndex > 0 && dotIndex < name.length - 1) name.substring(dotIndex + 1) else ""
}

fun File.nameWithoutExtension(): String {
    val dotIndex = name.lastIndexOf('.')
    return if (dotIndex > 0) name.substring(0, dotIndex) else name
}

fun File.hasExtension(extension: String): Boolean {
    val cleanExtension = extension.removePrefix(".")
    return this.extension().equals(cleanExtension, ignoreCase = true)
}

@WorkerThread
@Throws(SecurityException::class)
fun File.createDirectories(): Boolean {
    return try {
        if (exists()) isDirectory else { Path(absolutePath).createDirectories(); true }
    } catch (e: IOException) { mkdirs() && exists() && isDirectory }
}

@WorkerThread
@Throws(SecurityException::class)
fun File.safeDelete(recursively: Boolean = false): Boolean {
    if (!exists()) return true
    return if (isDirectory && recursively) { listFiles()?.all { it.safeDelete(true) } ?: true && delete() } else { delete() }
}

fun File.isReadable(): Boolean = exists() && canRead()
fun File.isWritable(): Boolean = exists() && canWrite()
fun File.sizeInBytes(): Long = if (exists() && isFile) length() else 0L
fun File.isEmpty(): Boolean = exists() && isFile && length() == 0L


