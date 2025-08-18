package com.lambao.presentation.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lambao.data.network.NetworkException
import com.lambao.presentation.handler.camera.CameraHandler
import com.lambao.presentation.handler.camera.CameraHandlerImpl
import com.lambao.presentation.handler.camera.CameraResult
import com.lambao.presentation.handler.dialog.DialogHandler
import com.lambao.presentation.handler.dialog.DialogHandlerImpl
import com.lambao.presentation.handler.loading.LoadingDialogHandler
import com.lambao.presentation.handler.loading.LoadingHandler
import com.lambao.presentation.handler.media.CustomPickMultipleVisualMedia
import com.lambao.presentation.handler.media.MediaPickerHandler
import com.lambao.presentation.handler.media.MediaPickerHandlerImpl
import com.lambao.presentation.handler.media.MediaPickerResult
import com.lambao.presentation.handler.network_error.NetworkErrorHandler
import com.lambao.presentation.handler.network_error.NetworkErrorHandlerImpl
import com.lambao.presentation.handler.permission.PermissionHandler
import com.lambao.presentation.handler.permission.PermissionHandlerImpl
import com.lambao.presentation.handler.permission.PermissionResult

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding: B
        get() = _binding
            ?: throw IllegalStateException("Binding in ${this::class.java.simpleName} is null")

    private val settingsLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (permissionHandler.isPermissionsGranted()) {
                permissionHandler.onPermissionResult(
                    PermissionResult.Granted(permissionHandler.getPermissions())
                )
            }
        }

    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsResult ->
            val grantedPermissions = permissionsResult.filter { it.value }.keys.toList()
            val deniedPermissions = permissionsResult.filter { !it.value }.keys.toList()
            if (deniedPermissions.isEmpty()) {
                permissionHandler.onPermissionResult(PermissionResult.Granted(grantedPermissions))
            } else {
                val permanentlyDenied = deniedPermissions.any {
                    !ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        it
                    )
                }
                if (permanentlyDenied) {
                    permissionHandler.promptOpenSettings()
                }
                permissionHandler.onPermissionResult(
                    PermissionResult.Denied(deniedPermissions, permanentlyDenied)
                )
            }
        }

    private val customMultipleMediaContract = CustomPickMultipleVisualMedia()

    private val mediaLauncher: ActivityResultLauncher<PickVisualMediaRequest> =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                mediaPickerHandler.onResult(MediaPickerResult.Success(listOf(uri)))
            }
        }

    private val multipleMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest> =
        registerForActivityResult(customMultipleMediaContract) { uris ->
            if (uris.isNotEmpty()) {
                mediaPickerHandler.onResult(MediaPickerResult.Success(uris))
            }
        }

    private val cameraLauncher: ActivityResultLauncher<Uri> =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                cameraHandler.onResult(CameraResult.Success(cameraHandler.uri))
            }
        }

    protected open val dialogHandler: DialogHandler by lazy {
        DialogHandlerImpl(this)
    }

    protected open val loadingHandler: LoadingHandler by lazy {
        LoadingDialogHandler(this)
    }

    protected open val networkErrorHandler: NetworkErrorHandler by lazy {
        NetworkErrorHandlerImpl(
            this,
            dialogHandler
        )
    }

    protected val permissionHandler: PermissionHandler by lazy {
        PermissionHandlerImpl(
            settingsLauncher,
            requestPermissionLauncher,
            dialogHandler,
            this
        )
    }

    protected val mediaPickerHandler: MediaPickerHandler by lazy {
        MediaPickerHandlerImpl(
            mediaLauncher,
            multipleMediaLauncher,
            dialogHandler,
            customMultipleMediaContract,
            this
        )
    }

    protected val cameraHandler: CameraHandler by lazy {
        CameraHandlerImpl(
            cameraLauncher,
            dialogHandler,
            this
        )
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun onViewReady(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        onViewReady(savedInstanceState)
    }

    fun showLoading() {
        loadingHandler.showLoading()
    }

    fun hideLoading() {
        loadingHandler.hideLoading()
    }

    fun handleNetworkError(networkException: NetworkException) {
        networkErrorHandler.handleError(networkException)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) hideLoading()
        _binding = null
    }
}
