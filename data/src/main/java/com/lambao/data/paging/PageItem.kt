package com.lambao.data.paging

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Data class representing page item information for pagination.
 * 
 * This class contains metadata about items on a specific page, including
 * the count of items on the current page and total items across all pages.
 *
 * @param count Number of items on the current page
 * @param total Total number of items across all pages
 * @param perPage Number of items per page
 */
data class PageItem(
    @Expose 
    @SerializedName("count") 
    val count: Int? = null,
    
    @Expose 
    @SerializedName("total") 
    val total: Int? = null,
    
    @Expose 
    @SerializedName("per_page") 
    val perPage: Int? = null
)
