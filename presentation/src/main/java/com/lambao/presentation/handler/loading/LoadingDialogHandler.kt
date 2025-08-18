package com.lambao.presentation.handler.loading

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.lambao.presentation.ui.view.loading.LoadingDialog

class LoadingDialogHandler(private val activity: FragmentActivity) : LoadingHandler {
	private var dialog: LoadingDialog? = null

	override fun showLoading() {
		if (!activity.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) return
		if (dialog == null || dialog?.isShowing != true) {
			dialog = LoadingDialog(activity).apply { show() }
		}
	}

	override fun hideLoading() {
		dialog?.dismiss()
		dialog = null
	}
}
