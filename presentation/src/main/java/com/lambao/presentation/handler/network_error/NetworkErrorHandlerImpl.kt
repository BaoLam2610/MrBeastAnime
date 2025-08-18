package com.lambao.presentation.handler.network_error

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import com.lambao.data.network.NetworkException
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

	override fun handleErrorWithRetry(
		networkException: NetworkException,
		retryAction: () -> Unit
	) {
		if (shouldShowError(networkException)) {
			val message = getUserFriendlyMessage(networkException)
			val options = NetworkErrorOptions(
				showRetry = true,
				showSettings = !isNetworkAvailable(),
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
					onDismissListener = options.onDismiss
				)
			}
		}
	}

	override fun shouldShowError(networkException: NetworkException): Boolean {
		val message = networkException.message ?: ""
		return when {
			!isNetworkAvailable() -> true
			message.contains("500") -> true
			message.contains("502") -> true
			message.contains("503") -> true
			message.contains("504") -> true
			message.contains("timeout", ignoreCase = true) -> true
			message.contains("connection refused", ignoreCase = true) -> true
			message.contains("400") -> false
			message.contains("401") -> false
			message.contains("403") -> false
			message.contains("404") -> false
			message.contains("422") -> false
			else -> true
		}
	}

	override fun getUserFriendlyMessage(networkException: NetworkException): String {
		val message = networkException.message ?: ""
		return when {
			!isNetworkAvailable() -> "No internet connection. Please check your network settings."
			message.contains("500") -> "Server error. Please try again later."
			message.contains("502") -> "Bad gateway. Please try again later."
			message.contains("503") -> "Service unavailable. Please try again later."
			message.contains("504") -> "Gateway timeout. Please try again later."
			message.contains("timeout", ignoreCase = true) -> "Request timed out. Please try again."
			message.contains("connection refused", ignoreCase = true) -> "Connection refused. Please try again."
			message.contains("400") -> "Invalid request. Please check your input."
			message.contains("401") -> "Authentication required. Please log in again."
			message.contains("403") -> "Access denied. You don't have permission for this action."
			message.contains("404") -> "Resource not found. Please check and try again."
			message.contains("422") -> "Invalid data. Please check your input."
			else -> message.ifBlank { "Network error occurred. Please try again." }
		}
	}

	private fun getDefaultErrorTitle(@Suppress("UNUSED_PARAMETER") networkException: NetworkException): String {
		return if (!isNetworkAvailable()) "No Internet Connection" else "Network Error"
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
			negativeText = "Cancel",
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
			negativeText = "Cancel",
			onPositiveListener = options.onSettings,
			onDismissListener = options.onDismiss
		)
	}

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
