package com.lambao.domain.exception

/**
 * Exception thrown when required parameters are empty or null.
 * 
 * This exception is used in the domain layer to indicate that
 * a use case cannot execute due to missing or invalid parameters.
 *
 * @param message Custom error message (optional)
 */
data class ParamsEmptyException(
    override val message: String? = "Required parameters cannot be empty"
) : Throwable(message)
