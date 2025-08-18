package com.lambao.presentation.handler.media

import android.net.Uri

/**
 * Sealed class representing the result of a media picker operation.
 * 
 * This class provides a type-safe way to handle different media picker outcomes
 * in the presentation layer.
 */
sealed class MediaPickerResult {
    /**
     * Media picker operation completed successfully.
     * 
     * @param uris List of URIs for the selected media files
     */
    data class Success(val uris: List<Uri>) : MediaPickerResult()
    
    /**
     * Media picker operation failed.
     * 
     * @param message Error message describing what went wrong
     */
    data class Error(val message: String) : MediaPickerResult()
}
