package com.lambao.core.error.network

/**
 * Exception class representing network-related errors.
 *
 * This class extends Throwable and provides additional context about network errors,
 * including the error type and HTTP status code.
 *
 * @param type The type of network error that occurred
 * @param code HTTP status code (nullable for non-HTTP errors)
 * @param message Human-readable error message
 * @param cause The underlying cause of the exception
 */
data class NetworkException(
    val type: NetworkErrorType,
    val code: Int? = null,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message, cause) {

    /**
     * Create a NetworkException with just type and message.
     */
    constructor(type: NetworkErrorType, message: String?) : this(type, null, message, null)

    /**
     * Create a NetworkException with type, code, and message.
     */
    constructor(type: NetworkErrorType, code: Int, message: String?) : this(type, code, message, null)
}


