package com.lambao.core.error.local

/**
 * Enum representing different types of local storage errors.
 *
 * This enum categorizes various errors that can occur when accessing
 * local storage, such as databases, shared preferences, or files.
 */
enum class LocalErrorType {
    /** Permission denied when accessing storage */
    PERMISSION_DENIED,

    /** Storage is unavailable or corrupted */
    STORAGE_UNAVAILABLE,

    /** Database query or operation failed */
    QUERY_FAILED,

    /** Data corruption or integrity issues */
    DATA_CORRUPTED,

    /** Storage is full */
    STORAGE_FULL,

    /** Unknown or unexpected error */
    UNKNOWN
}


