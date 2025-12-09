package com.lambao.base.presentation.ui.viewmodel.paging

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A unified base class for pagination view models that abstracts common functionality
 * between remote and client-side pagination approaches.
 *
 * @param T The type of items being paginated
 * @param dispatcherProvider Provider for coroutine dispatchers
 */
abstract class UnifiedBasePagingViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : BaseManualPagingViewModel(dispatcherProvider), PagingDelegate {

    // Common state for items shown to the user
    private val _items = MutableStateFlow<List<T>>(emptyList())
    val items = _items.asStateFlow()

    // Common pagination configuration
    private val _currentPage = MutableStateFlow(1)
    override val currentPage = _currentPage.asStateFlow()

    private val _pageSize = MutableStateFlow(20)
    override val pageSize = _pageSize.asStateFlow()

    /**
     * Abstract method to load data based on the specific pagination strategy
     */
    abstract fun fetchData()

    override fun loadMoreItems() {
        fetchData()
    }

    /**
     * Sets the current page size for pagination
     *
     * @param size The number of items per page
     */
    fun setPageSize(size: Int) {
        _pageSize.value = size
    }

    /**
     * Resets pagination to initial state
     */
    open fun resetPaging() {
        _currentPage.value = 1
        _items.value = emptyList()
    }

    /**
     * Updates the current page value
     *
     * @param page The new page number
     */
    protected fun setCurrentPage(page: Int) {
        _currentPage.value = page
    }

    /**
     * Increments the current page number by 1
     */
    protected fun increaseCurrentPage() {
        _currentPage.value += 1
    }

    /**
     * Updates the items list with new items
     *
     * @param newItems The list of items to be displayed
     * @param append Whether to append the new items to the existing list or replace them
     */
    protected fun updateItems(newItems: List<T>, append: Boolean = true) {
        _items.value = if (append) {
            _items.value + newItems
        } else {
            newItems
        }
    }
}