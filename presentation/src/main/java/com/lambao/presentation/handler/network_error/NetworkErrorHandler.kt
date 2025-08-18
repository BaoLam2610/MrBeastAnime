package com.lambao.presentation.handler.network_error

import com.lambao.data.network.NetworkException

/**
 * Interface for handling network errors in the presentation layer.
 */
interface NetworkErrorHandler {
	/** Handle a network error with default behavior. */
	fun handleError(networkException: NetworkException)

	/** Handle a network error with a retry action. */
	fun handleErrorWithRetry(
		networkException: NetworkException,
		retryAction: () -> Unit
	)

	/** Handle a network error with custom options. */
	fun handleErrorWithOptions(
		networkException: NetworkException,
		options: NetworkErrorOptions
	)

	/** Determine if an error should be shown to the user. */
	fun shouldShowError(networkException: NetworkException): Boolean

	/** Get a user-friendly message for the network exception. */
	fun getUserFriendlyMessage(networkException: NetworkException): String
}

/**
 * Configuration options for network error handling.
 */
data class NetworkErrorOptions(
	val showRetry: Boolean = true,
	val showSettings: Boolean = false,
	val customTitle: String? = null,
	val customMessage: String? = null,
	val retryText: String = "Retry",
	val settingsText: String = "Settings",
	val onRetry: (() -> Unit)? = null,
	val onSettings: (() -> Unit)? = null,
	val onDismiss: (() -> Unit)? = null
)
