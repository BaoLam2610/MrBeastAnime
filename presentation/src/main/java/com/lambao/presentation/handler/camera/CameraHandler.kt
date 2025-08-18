package com.lambao.presentation.handler.camera

import android.net.Uri

/**
 * Interface for handling camera operations in the presentation layer.
 * 
 * This interface provides methods for opening the camera and handling
 * camera results in a clean, testable way.
 */
interface CameraHandler {
    /**
     * The URI where the camera will save the captured image.
     */
    val uri: Uri
    
    /**
     * Open the camera to capture an image.
     * 
     * @param onResult Callback function to handle the camera result
     */
    fun openCamera(onResult: (CameraResult) -> Unit)
    
    /**
     * Handle the camera result from the activity result launcher.
     * 
     * @param result The result from the camera operation
     */
    fun onResult(result: CameraResult)
}
