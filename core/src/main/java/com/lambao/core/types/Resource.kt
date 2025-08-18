package com.lambao.core.types

/**
 * A sealed class representing the state of a data operation.
 *
 * This class provides a type-safe way to handle different states of asynchronous operations,
 * including success, error, loading, and initialization states.
 *
 * Note: The [pageInfo] field is intentionally typed as [Any?] to avoid coupling core to
 * any specific paging representation from other modules.
 */
sealed class Resource<T>(
    val data: T? = null,
    val pageInfo: PageInfo? = null,
    val message: String? = null,
    val throwable: Throwable? = null
) {
    /** Initial state before any operation begins */
    class Init<T> : Resource<T>()

    /** Loading state during operation execution */
    class Loading<T>(data: T? = null) : Resource<T>(data = data)

    /** Success state when operation completes successfully */
    class Success<T>(
        data: T?,
        pageInfo: PageInfo? = null,
        message: String? = null
    ) : Resource<T>(data = data, pageInfo = pageInfo, message = message)

    /** Error state when operation fails */
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
 */
inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Init -> Resource.Init()
        is Resource.Loading -> Resource.Loading(data?.let(transform))
        is Resource.Success -> Resource.Success(data?.let(transform), pageInfo, message)
        is Resource.Error -> Resource.Error(
            data?.let(transform),
            message,
            throwable ?: Throwable("Unknown error")
        )
    }
}

/**
 * Maps a Resource containing a List to another List using the provided transform function.
 */
inline fun <T, R> Resource<List<T>>.mapList(transform: (T) -> R): Resource<List<R>> {
    return when (this) {
        is Resource.Init -> Resource.Init()
        is Resource.Loading -> Resource.Loading(data?.map(transform))
        is Resource.Success -> Resource.Success(data?.map(transform), pageInfo, message)
        is Resource.Error -> Resource.Error(
            data?.map(transform),
            message,
            throwable ?: Throwable("Unknown error")
        )
    }
}


