package com.lambao.data.network

import com.lambao.core.dispatcher.DispatcherProvider
import com.lambao.core.types.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Base class for remote data sources that handle API calls.
 * 
 * This class provides common functionality for making safe API calls,
 * handling errors, and managing coroutine dispatchers.
 *
 * @param dispatcherProvider Provider for coroutine dispatchers
 * @param errorParser Parser for error responses (injectable for testing)
 */
abstract class BaseRemoteDataSource(
    private val dispatcherProvider: DispatcherProvider,
    private val errorParser: ErrorResponseParser = ErrorResponseParser()
) {
    /**
     * Use IO dispatcher for network operations (API calls, HTTP requests).
     * These operations are I/O bound and should not block the main thread.
     */
    protected open val coroutineDispatcher get() = dispatcherProvider.ioDispatcher
    
    /**
     * Make a safe API call using Retrofit Response.
     * 
     * @param apiCall The API call function to execute
     * @return Flow of Resource containing the result
     */
    protected open fun <T> safeCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        val response = apiCall()
        
        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
        } else {
            emit(Resource.Error(throwable = errorParser.parseErrorResponse(response)))
        }
    }.flowOn(coroutineDispatcher)
        .catch { e -> emit(Resource.Error(throwable = mapExceptionToNetworkError(e))) }
    
    /**
     * Make a safe API call using ApiResponse wrapper.
     * 
     * @param apiCall The API call function to execute
     * @return Flow of Resource containing the result
     */
    protected open fun <T> safeApiCall(apiCall: suspend () -> ApiResponse<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        val response = apiCall()
        
        if (response.isSuccess) {
            emit(Resource.Success<T>(data = response.data, paging = response.pagination))
        } else {
            emit(Resource.Error<T>(throwable = errorParser.parseErrorResponse(response)))
        }
    }.flowOn(coroutineDispatcher)
        .catch { e -> emit(Resource.Error(throwable = mapExceptionToNetworkError(e))) }
    
    /**
     * Map exceptions to NetworkException objects.
     * 
     * @param e The exception to map
     * @return NetworkException with appropriate error type
     */
    protected open fun mapExceptionToNetworkError(e: Throwable): NetworkException {
        return when (e) {
            is HttpException -> createHttpException(e)
            is IOException -> createNetworkException(e)
            else -> createUnknownException(e)
        }
    }
    
    /**
     * Create NetworkException from HttpException.
     */
    private fun createHttpException(e: HttpException): NetworkException {
        return NetworkException(
            type = NetworkErrorType.fromCode(e.code()),
            code = e.code(),
            message = e.message
        )
    }
    
    /**
     * Create NetworkException from IOException (network issues).
     */
    private fun createNetworkException(e: IOException): NetworkException {
        return NetworkException(
            type = NetworkErrorType.NO_NETWORK,
            message = e.message
        )
    }
    
    /**
     * Create NetworkException from unknown exceptions.
     */
    private fun createUnknownException(e: Throwable): NetworkException {
        return NetworkException(
            type = NetworkErrorType.UNKNOWN,
            message = e.message
        )
    }
}
