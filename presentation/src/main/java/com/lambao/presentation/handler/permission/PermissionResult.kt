package com.lambao.presentation.handler.permission

/**
 * Sealed class representing the result of a permission request operation.
 * 
 * This class provides a type-safe way to handle different permission outcomes
 * in the presentation layer.
 */
sealed class PermissionResult {
    /**
     * Permission request was granted.
     * 
     * @param permissions List of permissions that were granted
     */
    data class Granted(val permissions: List<String>) : PermissionResult()
    
    /**
     * Permission request was denied.
     * 
     * @param permissions List of permissions that were denied
     * @param isPermanentlyDenied Whether the permissions were permanently denied
     */
    data class Denied(
        val permissions: List<String>,
        val isPermanentlyDenied: Boolean
    ) : PermissionResult()
}
