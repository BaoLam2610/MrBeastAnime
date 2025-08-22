package com.lambao.presentation.handler.network_error

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.annotation.RequiresPermission
import com.lambao.core.error.network.NetworkErrorType
import com.lambao.core.error.network.NetworkException
import com.lambao.presentation.R
import com.lambao.presentation.handler.dialog.DialogHandler

class NetworkErrorHandlerImpl(
	private val context: Context,
	private val dialogHandler: DialogHandler
) : NetworkErrorHandler {

	override fun handleError(networkException: NetworkException) {
		if (shouldShowError(networkException)) {
			val message = getUserFriendlyMessage(networkException)
			dialogHandler.showAlertDialog(message = message)
		}
	}

	@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
	override fun handleErrorWithRetry(
		networkException: NetworkException,
		retryAction: () -> Unit
	) {
		if (shouldShowError(networkException)) {
			val message = getUserFriendlyMessage(networkException)
			val options = NetworkErrorOptions(
				showRetry = shouldShowRetry(networkException.type),
				showSettings = shouldShowSettings(networkException.type),
				customMessage = message,
				onRetry = retryAction,
				onSettings = { openNetworkSettings() }
			)
			handleErrorWithOptions(networkException, options)
		}
	}

	override fun handleErrorWithOptions(
		networkException: NetworkException,
		options: NetworkErrorOptions
	) {
		if (!shouldShowError(networkException)) return

		val title = options.customTitle ?: getDefaultErrorTitle(networkException)
		val message = options.customMessage ?: getUserFriendlyMessage(networkException)

		when {
			options.showRetry && options.showSettings -> {
				showRetryDialog(title, message, options)
				// Could add a custom 3-button dialog if needed
			}
			options.showRetry -> {
				showRetryDialog(title, message, options)
			}
			options.showSettings -> {
				showSettingsDialog(title, message, options)
			}
			else -> {
				dialogHandler.showAlertDialog(
					title = title,
					message = message,
					positiveText = context.getString(R.string.ok),
					negativeText = null,
					onDismissListener = options.onDismiss
				)
			}
		}
	}

	override fun shouldShowError(networkException: NetworkException): Boolean {
		return when (networkException.type) {
			NetworkErrorType.NO_NETWORK,
			NetworkErrorType.TIMEOUT,
			NetworkErrorType.SERVER_ERROR,
			NetworkErrorType.BAD_GATEWAY,
			NetworkErrorType.SERVICE_UNAVAILABLE,
			NetworkErrorType.GATEWAY_TIMEOUT -> true

			NetworkErrorType.BAD_REQUEST,
			NetworkErrorType.UNAUTHORIZED,
			NetworkErrorType.FORBIDDEN,
			NetworkErrorType.NOT_FOUND,
			NetworkErrorType.UNPROCESSABLE_ENTITY,
			NetworkErrorType.NOT_MODIFIED,
			NetworkErrorType.TOO_MANY_REQUESTS,
			NetworkErrorType.METHOD_NOT_ALLOWED -> false

			NetworkErrorType.NOT_IMPLEMENTED,
			NetworkErrorType.UNKNOWN -> true
		}
	}

	override fun getUserFriendlyMessage(networkException: NetworkException): String {
		return when (networkException.type) {
			NetworkErrorType.NO_NETWORK -> context.getString(R.string.error_network_no_connection)
			NetworkErrorType.TIMEOUT -> context.getString(R.string.error_network_timeout)
			NetworkErrorType.SERVER_ERROR -> context.getString(R.string.error_network_server_error)
			NetworkErrorType.BAD_GATEWAY -> context.getString(R.string.error_network_bad_gateway)
			NetworkErrorType.SERVICE_UNAVAILABLE -> context.getString(R.string.error_network_service_unavailable)
			NetworkErrorType.GATEWAY_TIMEOUT -> context.getString(R.string.error_network_gateway_timeout)
			NetworkErrorType.BAD_REQUEST -> networkException.message
				?: context.getString(R.string.error_network_bad_request)

			NetworkErrorType.UNAUTHORIZED -> networkException.message
				?: context.getString(R.string.error_network_unauthorized)

			NetworkErrorType.FORBIDDEN -> networkException.message
				?: context.getString(R.string.error_network_forbidden)

			NetworkErrorType.NOT_FOUND -> networkException.message
				?: context.getString(R.string.error_network_not_found)

			NetworkErrorType.UNPROCESSABLE_ENTITY -> networkException.message
				?: context.getString(R.string.error_network_unprocessable_entity)

			NetworkErrorType.METHOD_NOT_ALLOWED -> networkException.message
				?: context.getString(R.string.error_network_method_not_allowed)

			NetworkErrorType.NOT_MODIFIED -> networkException.message
				?: context.getString(R.string.error_network_not_modified)

			NetworkErrorType.TOO_MANY_REQUESTS -> networkException.message
				?: context.getString(R.string.error_network_too_many_requests)

			NetworkErrorType.NOT_IMPLEMENTED -> networkException.message
				?: context.getString(R.string.error_network_not_implemented)

			NetworkErrorType.UNKNOWN -> networkException.message
				?: context.getString(R.string.error_network_unknown)
		}
	}

	private fun getDefaultErrorTitle(networkException: NetworkException): String {
		return when (networkException.type) {
			NetworkErrorType.NO_NETWORK, NetworkErrorType.TIMEOUT -> context.getString(R.string.error_title_no_internet)
			NetworkErrorType.SERVER_ERROR, NetworkErrorType.BAD_GATEWAY, NetworkErrorType.SERVICE_UNAVAILABLE, NetworkErrorType.GATEWAY_TIMEOUT -> context.getString(
				R.string.error_title_server_error
			)

			NetworkErrorType.BAD_REQUEST, NetworkErrorType.UNAUTHORIZED, NetworkErrorType.FORBIDDEN, NetworkErrorType.NOT_FOUND, NetworkErrorType.UNPROCESSABLE_ENTITY, NetworkErrorType.NOT_MODIFIED, NetworkErrorType.TOO_MANY_REQUESTS, NetworkErrorType.METHOD_NOT_ALLOWED -> context.getString(
				R.string.error_title_request_error
			)

			NetworkErrorType.NOT_IMPLEMENTED, NetworkErrorType.UNKNOWN -> context.getString(R.string.error_title_network_error)
		}
	}

	private fun shouldShowRetry(type: NetworkErrorType): Boolean {
		return when (type) {
			NetworkErrorType.SERVER_ERROR,
			NetworkErrorType.BAD_GATEWAY,
			NetworkErrorType.SERVICE_UNAVAILABLE,
			NetworkErrorType.GATEWAY_TIMEOUT,
			NetworkErrorType.TIMEOUT -> true

			NetworkErrorType.NO_NETWORK -> false // prefer settings
			NetworkErrorType.BAD_REQUEST,
			NetworkErrorType.UNAUTHORIZED,
			NetworkErrorType.FORBIDDEN,
			NetworkErrorType.NOT_FOUND,
			NetworkErrorType.UNPROCESSABLE_ENTITY,
			NetworkErrorType.NOT_MODIFIED,
			NetworkErrorType.METHOD_NOT_ALLOWED,
			NetworkErrorType.TOO_MANY_REQUESTS,
			NetworkErrorType.NOT_IMPLEMENTED,
			NetworkErrorType.UNKNOWN -> false
		}
	}

	@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
	private fun shouldShowSettings(type: NetworkErrorType): Boolean {
		return type == NetworkErrorType.NO_NETWORK && !isNetworkAvailable()
	}

	private fun showRetryDialog(
		title: String,
		message: String,
		options: NetworkErrorOptions
	) {
		dialogHandler.showAlertDialog(
			title = title,
			message = message,
			positiveText = options.retryText,
			negativeText = context.getString(R.string.cancel),
			onPositiveListener = options.onRetry,
			onDismissListener = options.onDismiss
		)
	}

	private fun showSettingsDialog(
		title: String,
		message: String,
		options: NetworkErrorOptions
	) {
		dialogHandler.showAlertDialog(
			title = title,
			message = message,
			positiveText = options.settingsText,
			negativeText = context.getString(R.string.cancel),
			onPositiveListener = options.onSettings,
			onDismissListener = options.onDismiss
		)
	}

	@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isNetworkAvailable(): Boolean {
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val network = connectivityManager.activeNetwork ?: return false
		val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

		return when {
			activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
			activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
			activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
			else -> false
		}
	}

	private fun openNetworkSettings() {
		try {
			val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
			context.startActivity(intent)
		} catch (_: Exception) {
			val intent = Intent(Settings.ACTION_SETTINGS)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
			context.startActivity(intent)
		}
	}
}
