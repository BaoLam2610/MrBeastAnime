package com.lambao.presentation.ui.viewmodel.paging

import kotlinx.coroutines.flow.StateFlow

/**
 * Interface for ViewModels that implement manual paging functionality.
 * 
 * This interface provides methods for managing pagination state and loading
 * additional items in a clean, testable way.
 */
interface PagingDelegate {
    /**
     * The current page number being displayed.
     */
    val currentPage: StateFlow<Int>
    
    /**
     * The number of items per page.
     */
    val pageSize: StateFlow<Int>
    
    /**
     * Load the next page of items.
     */
    fun loadMoreItems()
    
    /**
     * Check if there are more items available to load.
     * 
     * @return True if there are more items, false otherwise
     */
    fun hasMoreItems(): Boolean
}
