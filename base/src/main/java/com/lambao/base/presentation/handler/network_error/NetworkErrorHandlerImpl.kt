package com.lambao.base.presentation.handler.network_error

import android.content.Context
import com.lambao.base.data.remote.NetworkException
import com.lambao.base.presentation.handler.dialog.DialogHandler

class NetworkErrorHandlerImpl(
    private val context: Context,
    private val dialogHandler: DialogHandler
) : NetworkErrorHandler {
    override fun handleError(networkException: NetworkException) {
        networkException.message?.let { dialogHandler.showAlertDialog(it) }
    }
}