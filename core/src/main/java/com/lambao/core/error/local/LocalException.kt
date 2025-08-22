package com.lambao.core.error.local

/**
 * Exception class representing local storage errors.
 *
 * This class extends Throwable and provides context about local storage errors,
 * including the error type and additional details.
 *
 * @param type The type of local storage error that occurred
 * @param message Technical error message from the original exception
 * @param cause The underlying cause of the exception
 */
data class LocalException(
    val type: LocalErrorType,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message, cause) {

    /**
     * Create a LocalException with just type and message.
     */
    constructor(type: LocalErrorType, message: String?) : this(type, message, null)
}


