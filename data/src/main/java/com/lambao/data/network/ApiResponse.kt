package com.lambao.data.network

import com.google.gson.annotations.SerializedName
import com.lambao.data.paging.Pagination

/**
 * Generic API response wrapper for all API calls.
 * 
 * This class represents the standard response format from the API,
 * including data, pagination, status, and error information.
 *
 * @param T The type of data contained in the response
 * @param pagination Pagination information for list responses
 * @param data The actual response data
 * @param status HTTP status code
 * @param type Response type identifier
 * @param messages Additional error messages or validation details
 * @param error Error message if the request failed
 */
data class ApiResponse<T>(
    @SerializedName("pagination") 
    val pagination: Pagination? = null,
    
    @SerializedName("data") 
    val data: T? = null,
    
    @SerializedName("status") 
    val status: Int? = null,
    
    @SerializedName("type") 
    val type: String? = null,
    
    @SerializedName("messages") 
    val messages: Map<String, List<String>>? = null,
    
    @SerializedName("error") 
    val error: String? = null
) {
    /**
     * Check if the response indicates success.
     * 
     * @return true if the response contains data and no error, false otherwise
     */
    val isSuccess: Boolean
        get() = data != null && error.isNullOrBlank()
    
    /**
     * Check if the response contains pagination information.
     * 
     * @return true if pagination is present, false otherwise
     */
    val hasPagination: Boolean
        get() = pagination != null
    
    /**
     * Get all error messages as a single string.
     * 
     * @return Combined error messages or null if no errors
     */
    fun getAllErrorMessages(): String? {
        return when {
            !error.isNullOrBlank() -> error
            !messages.isNullOrEmpty() -> {
                messages.flatMap { (_, messageList) -> messageList }
                    .joinToString("\n")
                    .takeIf { it.isNotBlank() }
            }
            else -> null
        }
    }
}
