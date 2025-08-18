package com.lambao.presentation.handler.dialog

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

class DialogHandlerImpl(
    private val activity: FragmentActivity
) : DialogHandler {

    private var currentDialog: AlertDialog? = null

    override fun showRationaleDialog(
        title: String,
        permissionDescription: String,
        positiveText: String,
        negativeText: String,
        onPositiveListener: (() -> Unit)?,
        onNegativeListener: (() -> Unit)?
    ) {
        showAlertDialog(
            title = title,
            message = permissionDescription,
            positiveText = positiveText,
            negativeText = negativeText,
            cancelable = false,
            onPositiveListener = onPositiveListener,
            onNegativeListener = onNegativeListener
        )
    }

    override fun showAlertDialog(message: String) {
        showAlertDialog(
            title = null,
            message = message,
            positiveText = null,
            negativeText = null,
            cancelable = false
        )
    }

    override fun showAlertDialog(
        title: String?,
        message: String,
        positiveText: String?,
        negativeText: String?,
        cancelable: Boolean,
        onPositiveListener: (() -> Unit)?,
        onNegativeListener: (() -> Unit)?,
        onDismissListener: (() -> Unit)?
    ) {
        if (!activity.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            return
        }

        currentDialog?.dismiss()

        val builder = AlertDialog.Builder(activity)
            .setMessage(message)
            .setCancelable(cancelable)

        title?.let { builder.setTitle(it) }

        positiveText?.let {
            builder.setPositiveButton(it) { dialog, _ ->
                onPositiveListener?.invoke()
                dialog.dismiss()
            }
        }

        negativeText?.let {
            builder.setNegativeButton(it) { dialog, _ ->
                onNegativeListener?.invoke()
                dialog.dismiss()
            }
        }

        currentDialog = builder.create().also { dialog ->
            dialog.setOnDismissListener { onDismissListener?.invoke(); currentDialog = null }
            dialog.show()
        }
    }
}


