package com.lambao.presentation.handler.media

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.fragment.app.FragmentActivity
import com.lambao.presentation.R
import com.lambao.presentation.handler.dialog.DialogHandler

/**
 * Implementation of MediaPickerHandler for managing media picker operations.
 * 
 * This class handles opening the media picker, managing the picker launchers,
 * and processing media picker results in a clean, testable way.
 *
 * @param mediaLauncher The activity result launcher for single media selection
 * @param multipleMediaLauncher The activity result launcher for multiple media selection
 * @param dialogHandler Handler for showing dialogs
 * @param customMultipleMediaContract Custom contract for multiple media selection
 * @param activity The fragment activity context
 */
open class MediaPickerHandlerImpl(
    private val mediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
    private val multipleMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
    private val dialogHandler: DialogHandler,
    private val customMultipleMediaContract: CustomPickMultipleVisualMedia,
    private val activity: FragmentActivity
) : MediaPickerHandler {

    private var onMediaResult: ((MediaPickerResult) -> Unit)? = null

    override fun pickMedia(
        mediaType: PickVisualMediaRequest.Builder.() -> Unit,
        maxItems: Int,
        onResult: ((MediaPickerResult) -> Unit)?
    ) {
        try {
            val request = PickVisualMediaRequest.Builder()
                .apply(mediaType)
                .build()
            onMediaResult = onResult
            
            if (maxItems > 1) {
                customMultipleMediaContract.updateMaxItems(maxItems)
                multipleMediaLauncher.launch(request)
                return
            }
            mediaLauncher.launch(request)
        } catch (e: Exception) {
            dialogHandler.showAlertDialog(
                message = activity.getString(R.string.failed_to_open_media_picker)
            )
            onResult?.invoke(MediaPickerResult.Error(e.message ?: ""))
        }
    }

    override fun onResult(result: MediaPickerResult) {
        onMediaResult?.invoke(result)
        onMediaResult = null
    }
}
