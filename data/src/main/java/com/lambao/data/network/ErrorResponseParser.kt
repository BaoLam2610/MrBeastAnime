package com.lambao.data.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

/**
 * Utility class for parsing error responses from API calls.
 * 
 * This class handles the parsing of error responses and converts them
 * into NetworkException objects for consistent error handling.
 */
class ErrorResponseParser(private val jsonParser: Gson = Gson()) {
    
    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error"
        private const val DEFAULT_ERROR_CODE = 0
    }
    
    /**
     * Parse error response from a Retrofit Response object.
     * 
     * @param response The Retrofit Response object
     * @return NetworkException with parsed error information
     */
    fun <T> parseErrorResponse(response: Response<T>): NetworkException {
        return try {
            val type = object : TypeToken<ApiResponse<T>>() {}.type
            val errorResponse: ApiResponse<T>? = jsonParser.fromJson(
                response.errorBody()?.charStream(), 
                type
            )
            
            val code = errorResponse?.status ?: response.code()
            val message = buildErrorMessage(errorResponse)
            
            NetworkException(
                type = NetworkErrorType.fromCode(code),
                code = code,
                message = message
            )
        } catch (e: Exception) {
            NetworkException(
                type = NetworkErrorType.UNKNOWN,
                code = response.code(),
                message = e.message ?: response.message()
            )
        }
    }
    
    /**
     * Parse error response from an ApiResponse object.
     * 
     * @param response The ApiResponse object
     * @return NetworkException with parsed error information
     */
    fun <T> parseErrorResponse(response: ApiResponse<T>): NetworkException {
        val code = response.status ?: DEFAULT_ERROR_CODE
        val message = buildErrorMessage(response)
        
        return NetworkException(
            type = NetworkErrorType.fromCode(code),
            code = code,
            message = message
        )
    }
    
    /**
     * Build error message from ApiResponse.
     * 
     * @param response The ApiResponse object
     * @return Formatted error message
     */
    private fun <T> buildErrorMessage(response: ApiResponse<T>?): String {
        return when {
            response == null -> DEFAULT_ERROR_MESSAGE
            !response.error.isNullOrBlank() -> response.error
            !response.messages.isNullOrEmpty() -> {
                response.messages
                    .flatMap { it.value }
                    .joinToString("\n")
                    .trim()
                    .takeIf { it.isNotBlank() }
                    ?: DEFAULT_ERROR_MESSAGE
            }
            else -> DEFAULT_ERROR_MESSAGE
        }
    }
}
