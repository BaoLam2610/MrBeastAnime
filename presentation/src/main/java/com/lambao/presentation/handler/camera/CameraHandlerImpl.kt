package com.lambao.presentation.handler.camera

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import com.lambao.presentation.R
import com.lambao.presentation.handler.dialog.DialogHandler
import java.io.File

/**
 * Implementation of CameraHandler for managing camera operations.
 * 
 * This class handles opening the camera, managing the image URI,
 * and processing camera results in a clean, testable way.
 *
 * @param cameraLauncher The activity result launcher for camera operations
 * @param dialogHandler Handler for showing dialogs
 * @param activity The fragment activity context
 */
class CameraHandlerImpl(
    private val cameraLauncher: ActivityResultLauncher<Uri>,
    private val dialogHandler: DialogHandler,
    private val activity: FragmentActivity
) : CameraHandler {
    
    private var onCameraResult: ((CameraResult) -> Unit)? = null

    /**
     * Lazy-initialized URI for the camera image file.
     * Creates a unique file name using timestamp.
     */
    private val _uri by lazy {
        val imageFile = File(activity.filesDir, "${System.currentTimeMillis()}.png")
        FileProvider.getUriForFile(
            activity,
            "${activity.packageName}.FileProvider",
            imageFile
        )
    }

    override val uri: Uri get() = _uri

    override fun openCamera(onResult: (CameraResult) -> Unit) {
        try {
            onCameraResult = onResult
            cameraLauncher.launch(_uri)
        } catch (e: Exception) {
            e.printStackTrace()
            dialogHandler.showAlertDialog(
                message = activity.getString(R.string.failed_to_open_camera) + ". " + (e.message ?: "")
            )
        }
    }

    override fun onResult(result: CameraResult) {
        onCameraResult?.invoke(result)
        onCameraResult = null
    }
}
