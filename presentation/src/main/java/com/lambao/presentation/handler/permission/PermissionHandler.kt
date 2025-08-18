package com.lambao.presentation.handler.permission

/**
 * Interface for handling permission operations in the presentation layer.
 * 
 * This interface provides methods for requesting, checking, and managing
 * Android permissions in a clean, testable way.
 */
interface PermissionHandler {
    /**
     * Get the list of permissions that this handler manages.
     * 
     * @return List of permission strings
     */
    fun getPermissions(): List<String>
    
    /**
     * Request a single permission.
     * 
     * @param permission The permission to request
     * @param settingsMessage Message to show when opening settings
     * @param onResult Callback function to handle the permission result
     */
    fun requestPermission(
        permission: String,
        settingsMessage: String? = null,
        onResult: ((PermissionResult) -> Unit)? = null
    )

    /**
     * Request multiple permissions.
     * 
     * @param permissions List of permissions to request
     * @param settingsMessage Message to show when opening settings
     * @param onResult Callback function to handle the permission result
     */
    fun requestPermissions(
        permissions: List<String>,
        settingsMessage: String? = null,
        onResult: ((PermissionResult) -> Unit)? = null
    )

    /**
     * Check if a specific permission is granted.
     * 
     * @param permission The permission to check
     * @return True if the permission is granted, false otherwise
     */
    fun checkPermissionGranted(permission: String): Boolean
    
    /**
     * Check if all managed permissions are granted.
     * 
     * @return True if all permissions are granted, false otherwise
     */
    fun isPermissionsGranted(): Boolean
    
    /**
     * Prompt the user to open app settings.
     */
    fun promptOpenSettings()
    
    /**
     * Handle the permission result from the activity result launcher.
     * 
     * @param result The result from the permission request
     */
    fun onPermissionResult(result: PermissionResult)
}
