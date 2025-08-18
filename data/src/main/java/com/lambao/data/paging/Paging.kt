package com.lambao.data.paging

import com.google.gson.annotations.SerializedName

/**
 * Interface defining pagination information for API responses.
 * 
 * This interface provides a contract for pagination data that can be implemented
 * by different pagination response formats.
 */
interface Paging {
    /** Whether there is a next page available */
    val hasNextPage: Boolean?
    
    /** Current page number */
    val currentPage: Int?
    
    /** Total number of pages */
    val totalPages: Int?
    
    /** Total number of items across all pages */
    val totalItems: Int?
    
    /** Number of items per page */
    val perPage: Int?
}

/**
 * Data class representing pagination information from API responses.
 * 
 * This class implements the Paging interface and provides serialization support
 * for JSON responses from the API.
 *
 * @param lastVisiblePage The last page number that contains data
 * @param hasNextPage Whether there is a next page available
 * @param currentPage The current page number
 * @param items Information about items on the current page
 */
data class Pagination(
    @SerializedName("last_visible_page") 
    val lastVisiblePage: Int? = null,
    
    @SerializedName("has_next_page") 
    override val hasNextPage: Boolean? = null,
    
    @SerializedName("current_page") 
    override val currentPage: Int? = null,
    
    @SerializedName("items") 
    val items: PageItem? = null
) : Paging {
    override val totalPages: Int? get() = lastVisiblePage
    override val totalItems: Int? get() = items?.total
    override val perPage: Int? get() = items?.perPage
}
