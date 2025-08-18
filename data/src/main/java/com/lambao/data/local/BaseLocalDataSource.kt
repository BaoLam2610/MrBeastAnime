package com.lambao.data.local

import com.lambao.core.dispatcher.DispatcherProvider
import com.lambao.data.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

/**
 * Base class for local data sources that handle local storage operations.
 * 
 * This class provides common functionality for making safe local storage calls,
 * handling errors, and managing coroutine dispatchers.
 *
 * @param dispatcherProvider Provider for coroutine dispatchers
 */
abstract class BaseLocalDataSource(
    private val dispatcherProvider: DispatcherProvider
) {
    /**
     * Use Default dispatcher for local operations (Room, SharedPreferences).
     * These operations are CPU-bound, not I/O bound.
     */
    protected open val coroutineDispatcher get() = dispatcherProvider.defaultDispatcher
    
    /**
     * Make a safe local storage call.
     * 
     * @param localCall The local storage operation to execute
     * @return Flow of Resource containing the result
     */
    open fun <T> safeCall(localCall: suspend () -> T): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        val result = localCall()
        emit(Resource.Success(data = result))
    }.flowOn(coroutineDispatcher)
        .catch { e -> emit(Resource.Error(throwable = mapToLocalException(e))) }
    
    /**
     * Map exceptions to LocalException objects.
     * 
     * @param e The exception to map
     * @return LocalException with appropriate error type
     */
    protected open fun mapToLocalException(e: Throwable): LocalException {
        return when (e) {
            is SecurityException -> createPermissionException(e)
            is IllegalStateException -> createQueryException(e)
            is IOException -> createStorageException(e)
            is OutOfMemoryError -> createStorageFullException(e)
            else -> createUnknownException(e)
        }
    }
    
    /**
     * Create LocalException for permission-related errors.
     */
    private fun createPermissionException(e: SecurityException): LocalException {
        return LocalException(
            type = LocalErrorType.PERMISSION_DENIED,
            message = e.message,  // ✅ Only technical message from exception
            cause = e
        )
    }
    
    /**
     * Create LocalException for query-related errors.
     */
    private fun createQueryException(e: IllegalStateException): LocalException {
        return LocalException(
            type = LocalErrorType.QUERY_FAILED,
            message = e.message,  // ✅ Only technical message from exception
            cause = e
        )
    }
    
    /**
     * Create LocalException for storage-related errors.
     */
    private fun createStorageException(e: IOException): LocalException {
        return LocalException(
            type = LocalErrorType.STORAGE_UNAVAILABLE,
            message = e.message,  // ✅ Only technical message from exception
            cause = e
        )
    }
    
    /**
     * Create LocalException for storage full errors.
     */
    private fun createStorageFullException(e: OutOfMemoryError): LocalException {
        return LocalException(
            type = LocalErrorType.STORAGE_FULL,
            message = e.message,  // ✅ Only technical message from exception
            cause = e
        )
    }
    
    /**
     * Create LocalException for unknown errors.
     */
    private fun createUnknownException(e: Throwable): LocalException {
        return LocalException(
            type = LocalErrorType.UNKNOWN,
            message = e.message,  // ✅ Only technical message from exception
            cause = e
        )
    }
}
