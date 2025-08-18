package com.lambao.presentation.handler.dialog

/**
 * Interface for handling dialog operations in the presentation layer.
 * 
 * This interface provides methods for showing various types of dialogs
 * in a clean, testable way.
 */
interface DialogHandler {
    /**
     * Show a rationale dialog for permission requests.
     * 
     * @param title Dialog title
     * @param permissionDescription Description of why the permission is needed
     * @param positiveText Text for the positive button
     * @param negativeText Text for the negative button
     * @param onPositiveListener Callback for positive button click
     * @param onNegativeListener Callback for negative button click
     */
    fun showRationaleDialog(
        title: String,
        permissionDescription: String,
        positiveText: String,
        negativeText: String,
        onPositiveListener: (() -> Unit)? = null,
        onNegativeListener: (() -> Unit)? = null
    )

    /**
     * Show a simple alert dialog with just a message.
     * 
     * @param message The message to display
     */
    fun showAlertDialog(message: String)
    
    /**
     * Show a customizable alert dialog.
     * 
     * @param title Dialog title (optional)
     * @param message The message to display
     * @param positiveText Text for the positive button (optional)
     * @param negativeText Text for the negative button (optional)
     * @param cancelable Whether the dialog can be cancelled
     * @param onPositiveListener Callback for positive button click
     * @param onNegativeListener Callback for negative button click
     * @param onDismissListener Callback when dialog is dismissed
     */
    fun showAlertDialog(
        title: String? = null,
        message: String,
        positiveText: String?,
        negativeText: String?,
        cancelable: Boolean = true,
        onPositiveListener: (() -> Unit)? = null,
        onNegativeListener: (() -> Unit)? = null,
        onDismissListener: (() -> Unit)? = null
    )
}
