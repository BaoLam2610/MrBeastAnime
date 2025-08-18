package com.lambao.presentation.handler.camera

import android.net.Uri

/**
 * Sealed class representing the result of a camera operation.
 * 
 * This class provides a type-safe way to handle different camera outcomes
 * in the presentation layer.
 */
sealed class CameraResult {
    /**
     * Camera operation completed successfully.
     * 
     * @param uri The URI of the captured image
     */
    data class Success(val uri: Uri) : CameraResult()
    
    /**
     * Camera operation failed.
     * 
     * @param message Error message describing what went wrong
     */
    data class Error(val message: String) : CameraResult()
}
