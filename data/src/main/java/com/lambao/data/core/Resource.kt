package com.lambao.data.core

import com.lambao.data.paging.Paging

/**
 * A sealed class representing the state of a data operation.
 * 
 * This class provides a type-safe way to handle different states of asynchronous operations,
 * including success, error, loading, and initialization states.
 *
 * @param T The type of data being wrapped
 * @param data The actual data payload (nullable)
 * @param paging Pagination information for list operations (nullable)
 * @param message Optional message for success/error states
 * @param throwable Exception information for error states
 */
sealed class Resource<T>(
    val data: T? = null,
    val paging: Paging? = null,
    val message: String? = null,
    val throwable: Throwable? = null
) {
    /**
     * Initial state before any operation begins
     */
    class Init<T> : Resource<T>()
    
    /**
     * Loading state during operation execution
     * @param data Optional cached data to show while loading
     */
    class Loading<T>(data: T? = null) : Resource<T>(data = data)

    /**
     * Success state when operation completes successfully
     * @param data The successful result data
     * @param paging Pagination information for list operations
     * @param message Optional success message
     */
    class Success<T>(
        data: T?, 
        paging: Paging? = null, 
        message: String? = null
    ) : Resource<T>(
        data = data,
        paging = paging,
        message = message
    )

    /**
     * Error state when operation fails
     * @param data Optional cached data to show despite error
     * @param message Error message
     * @param throwable The exception that caused the error
     */
    class Error<T>(
        data: T? = null, 
        message: String? = null, 
        throwable: Throwable
    ) : Resource<T>(
        data = data,
        message = message,
        throwable = throwable
    )
}

/**
 * Maps a Resource of one type to another using the provided transform function.
 * 
 * @param transform Function to transform the data from type T to R
 * @return A new Resource with transformed data
 */
inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Init -> Resource.Init()
        is Resource.Loading -> Resource.Loading(data?.let(transform))
        is Resource.Success -> Resource.Success(data?.let(transform), paging, message)
        is Resource.Error -> Resource.Error(
            data?.let(transform),
            message,
            throwable ?: Throwable("Unknown error")
        )
    }
}

/**
 * Maps a Resource containing a List to another List using the provided transform function.
 * 
 * @param transform Function to transform each item in the list
 * @return A new Resource with transformed list data
 */
inline fun <T, R> Resource<List<T>>.mapList(transform: (T) -> R): Resource<List<R>> {
    return when (this) {
        is Resource.Init -> Resource.Init()
        is Resource.Loading -> Resource.Loading(data?.map(transform))
        is Resource.Success -> Resource.Success(data?.map(transform), paging, message)
        is Resource.Error -> Resource.Error(
            data?.map(transform),
            message,
            throwable ?: Throwable("Unknown error")
        )
    }
}
