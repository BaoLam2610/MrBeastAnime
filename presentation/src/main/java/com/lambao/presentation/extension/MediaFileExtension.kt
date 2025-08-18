package com.lambao.presentation.extension

import android.content.Context
import android.webkit.MimeTypeMap
import androidx.annotation.WorkerThread
import java.io.File

enum class MediaFileType { IMAGE, PDF, VIDEO, TEXT, WORD, EXCEL, POWERPOINT, OTHER }

private val TEXT_EXTENSIONS = setOf("txt", "csv", "log", "md", "json", "xml")
private val WORD_EXTENSIONS = setOf("doc", "docx")
private val EXCEL_EXTENSIONS = setOf("xls", "xlsx", "csv")
private val POWERPOINT_EXTENSIONS = setOf("ppt", "pptx")
private val IMAGE_EXTENSIONS = setOf("jpg", "jpeg", "png", "gif", "bmp", "webp", "tiff")
private val PDF_EXTENSIONS = setOf("pdf")
private val VIDEO_EXTENSIONS = setOf("mp4", "avi", "mkv", "mov", "wmv", "flv", "3gp", "mpeg", "webm")

fun File.isText(extensions: Set<String> = TEXT_EXTENSIONS): Boolean = extension.lowercase() in extensions
fun File.isWord(extensions: Set<String> = WORD_EXTENSIONS): Boolean = extension.lowercase() in extensions
fun File.isExcel(extensions: Set<String> = EXCEL_EXTENSIONS): Boolean = extension.lowercase() in extensions
fun File.isPowerPoint(extensions: Set<String> = POWERPOINT_EXTENSIONS): Boolean = extension.lowercase() in extensions
fun File.isImage(extensions: Set<String> = IMAGE_EXTENSIONS): Boolean = extension.lowercase() in extensions
fun File.isPdf(): Boolean = extension.lowercase() in PDF_EXTENSIONS
fun File.isVideo(extensions: Set<String> = VIDEO_EXTENSIONS): Boolean = extension.lowercase() in extensions

@WorkerThread
fun File.isTextByMimeType(context: Context): Boolean {
    val mimeType = getMimeType(context) ?: return false
    return mimeType.startsWith("text/") || mimeType in setOf("application/json", "application/xml", "text/csv")
}

@WorkerThread
fun File.isWordByMimeType(context: Context): Boolean {
    val mimeType = getMimeType(context) ?: return false
    return mimeType in setOf(
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    )
}

@WorkerThread
fun File.isExcelByMimeType(context: Context): Boolean {
    val mimeType = getMimeType(context) ?: return false
    return mimeType in setOf(
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "text/csv"
    )
}

@WorkerThread
fun File.isImageByMimeType(context: Context): Boolean {
    val mimeType = getMimeType(context) ?: return false
    return mimeType.startsWith("image/")
}

@WorkerThread
fun File.isPdfByMimeType(context: Context): Boolean {
    val mimeType = getMimeType(context) ?: return false
    return mimeType == "application/pdf"
}

@WorkerThread
fun File.isVideoByMimeType(context: Context): Boolean {
    val mimeType = getMimeType(context) ?: return false
    return mimeType.startsWith("video/")
}

@WorkerThread
fun File.getMimeType(context: Context): String? {
    val extension = extension.lowercase()
    return if (extension.isNotEmpty()) {
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    } else null
}

fun File.getDocumentFileType(
    textExtensions: Set<String> = TEXT_EXTENSIONS,
    wordExtensions: Set<String> = WORD_EXTENSIONS,
    excelExtensions: Set<String> = EXCEL_EXTENSIONS,
    powerPointExtensions: Set<String> = POWERPOINT_EXTENSIONS,
    imageExtensions: Set<String> = IMAGE_EXTENSIONS,
    videoExtensions: Set<String> = VIDEO_EXTENSIONS
): MediaFileType {
    return when {
        isText(textExtensions) -> MediaFileType.TEXT
        isWord(wordExtensions) -> MediaFileType.WORD
        isExcel(excelExtensions) -> MediaFileType.EXCEL
        isPowerPoint(powerPointExtensions) -> MediaFileType.POWERPOINT
        isImage(imageExtensions) -> MediaFileType.IMAGE
        isPdf() -> MediaFileType.PDF
        isVideo(videoExtensions) -> MediaFileType.VIDEO
        else -> MediaFileType.OTHER
    }
}

@WorkerThread
fun File.getDocumentFileTypeByMimeType(context: Context): MediaFileType {
    return when {
        isTextByMimeType(context) -> MediaFileType.TEXT
        isWordByMimeType(context) -> MediaFileType.WORD
        isExcelByMimeType(context) -> MediaFileType.EXCEL
        isPowerPointByMimeType(context) -> MediaFileType.POWERPOINT
        isImageByMimeType(context) -> MediaFileType.IMAGE
        isPdfByMimeType(context) -> MediaFileType.PDF
        isVideoByMimeType(context) -> MediaFileType.VIDEO
        else -> MediaFileType.OTHER
    }
}

fun File.hasAnyExtension(extensions: Set<String>): Boolean = extension.lowercase() in extensions


