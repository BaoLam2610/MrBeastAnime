package com.lambao.presentation.handler.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.lambao.presentation.R
import com.lambao.presentation.handler.dialog.DialogHandler

/**
 * Implementation of PermissionHandler for managing Android permission operations.
 * 
 * This class handles requesting permissions, checking permission status,
 * and managing permission results in a clean, testable way.
 *
 * @param settingsLauncher The activity result launcher for opening app settings
 * @param requestPermissionLauncher The activity result launcher for requesting permissions
 * @param dialogHandler Handler for showing dialogs
 * @param activity The fragment activity context
 */
class PermissionHandlerImpl(
    private val settingsLauncher: ActivityResultLauncher<Intent>,
    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
    private val dialogHandler: DialogHandler,
    private val activity: FragmentActivity
) : PermissionHandler {

    private val permissions = mutableListOf<String>()
    private var onPermissionResult: ((PermissionResult) -> Unit)? = null
    private var settingsMessage: String = activity.getString(R.string.required_permissions_have_been_denied_please_enable_in_app_settings)

    override fun getPermissions() = permissions

    override fun requestPermission(
        permission: String,
        settingsMessage: String?,
        onResult: ((PermissionResult) -> Unit)?
    ) {
        if (permission.isEmpty()) {
            return
        }
        requestPermissions(listOf(permission), settingsMessage, onResult)
    }

    override fun requestPermissions(
        permissions: List<String>,
        settingsMessage: String?,
        onResult: ((PermissionResult) -> Unit)?
    ) {
        addPermissions(permissions)

        if (permissions.isEmpty()) {
            onResult?.invoke(PermissionResult.Granted(emptyList()))
            return
        }

        onPermissionResult = onResult
        settingsMessage?.let { this.settingsMessage = it }

        val notGrantedPermissions = permissions.filter { !checkPermissionGranted(it) }

        if (notGrantedPermissions.isEmpty()) {
            onResult?.invoke(PermissionResult.Granted(permissions))
            return
        }

        launchPermissionRequest(notGrantedPermissions)

        /*
        // Remove comment if you want to show rationale dialog
        val rationalePermissions = notGrantedPermissions.filter {
            ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
        }

        if (rationalePermissions.isNotEmpty()) {
            dialogHandler.showAlertDialog(
                title = activity.getString(R.string.permission_required),
                message = activity.getString(R.string.at_least_one_permission_must_be_provided),
                positiveText = activity.getString(R.string.grant),
                negativeText = activity.getString(R.string.cancel),
                onPositiveListener = { launchPermissionRequest(notGrantedPermissions) }
            )
        } else {
            launchPermissionRequest(notGrantedPermissions)
        }*/
    }

    private fun addPermissions(permissions: List<String>) {
        this.permissions.clear()
        this.permissions.addAll(permissions)
    }

    private fun launchPermissionRequest(permissions: List<String>) {
        requestPermissionLauncher.launch(permissions.toTypedArray())
    }

    override fun promptOpenSettings() {
        dialogHandler.showAlertDialog(
            message = settingsMessage,
            positiveText = activity.getString(R.string.settings),
            negativeText = activity.getString(R.string.cancel),
            onPositiveListener = { openAppSettings() }
        )
    }

    private fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", activity.packageName, null)
            }
            settingsLauncher.launch(intent)
        } catch (e: Exception) {
            dialogHandler.showAlertDialog(activity.getString(R.string.cannot_open_app_settings_please_open_manual))
        }
    }

    override fun checkPermissionGranted(permission: String): Boolean {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun isPermissionsGranted(): Boolean {
        return permissions.all { checkPermissionGranted(it) }
    }

    override fun onPermissionResult(result: PermissionResult) {
        onPermissionResult?.invoke(result)
    }
}
