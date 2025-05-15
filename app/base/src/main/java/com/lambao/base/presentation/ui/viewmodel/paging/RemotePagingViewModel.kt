package com.lambao.base.presentation.ui.viewmodel.paging

import com.lambao.base.data.remote.paging.Paging
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Specialized base class for remote pagination that fetches data from an API
 * with server-side pagination, supporting client-side sub-pagination.
 */
abstract class RemotePagingViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : UnifiedBasePagingViewModel<T>(dispatcherProvider) {

    private val _paging = MutableStateFlow<Paging?>(null)
    val paging = _paging.asStateFlow()

    // Buffer to store items fetched from API
    private val _bufferedItems = MutableStateFlow<List<T>>(emptyList())

    // Tracks the display page for client-side sub-pagination
    private val _displayPage = MutableStateFlow(1)
    val displayPage = _displayPage.asStateFlow()

    // Desired number of items to display per page (client-side)
    protected open val displayPageSize = 10

    /**
     * Checks if more items can be loaded, either from buffer or API.
     *
     * @return True if more items are available, false if loading or no more items
     */
    override fun hasMoreItems(): Boolean {
        if (isLoadingScreenState()) return false
        // Check buffer first, then API paging
        return _bufferedItems.value.isNotEmpty() || (_paging.value?.hasNextPage == true)
    }

    /**
     * Loads additional items, either from buffer or by fetching from API.
     */
    override fun loadMoreItems() {
        if (isLoadingScreenState()) return

        // If buffer has items, take from buffer
        if (_bufferedItems.value.isNotEmpty()) {
            val itemsToDisplay = _bufferedItems.value.take(displayPageSize)
            updateItems(itemsToDisplay, append = true)
            _bufferedItems.value = _bufferedItems.value.drop(displayPageSize)
            _displayPage.value += 1 // Increase display page for sub-pagination
        } else {
            // Buffer empty, fetch new page from API
            fetchData()
        }
    }

    /**
     * Sets pagination metadata received from the API.
     *
     * @param paging The pagination information from the API
     */
    fun setPaging(paging: Paging) {
        _paging.value = paging
    }

    /**
     * Adds new items from API to the buffer and loads the first chunk.
     *
     * @param data The new items from the API
     */
    fun appendItems(data: List<T>) {
        _bufferedItems.value = data
        _displayPage.value = 1 // Reset display page for new buffer
        increaseCurrentPage() // Increase API page
        // Load first chunk immediately
        loadMoreItems()
    }

    /**
     * Resets pagination to its initial state, clearing paging metadata and buffer.
     */
    override fun resetPaging() {
        super.resetPaging()
        _paging.value = null
        _bufferedItems.value = emptyList()
        _displayPage.value = 1
    }
}