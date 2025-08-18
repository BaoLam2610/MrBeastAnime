package com.lambao.presentation.handler.media

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

/**
 * Interface for handling media picker operations in the presentation layer.
 * 
 * This interface provides methods for picking media files (images/videos)
 * in a clean, testable way.
 */
interface MediaPickerHandler {
    /**
     * Pick media files from the device.
     * 
     * @param mediaType Configuration for the media type to pick
     * @param maxItems Maximum number of items that can be selected
     * @param onResult Callback function to handle the pick result
     */
    fun pickMedia(
        mediaType: PickVisualMediaRequest.Builder.() -> Unit = {
            setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
        },
        maxItems: Int = 1,
        onResult: ((MediaPickerResult) -> Unit)? = null
    )

    /**
     * Handle the media picker result from the activity result launcher.
     * 
     * @param result The result from the media picker operation
     */
    fun onResult(result: MediaPickerResult)
}
