package com.lambao.base.presentation.ui.viewmodel.paging

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Specialized base class for client-side pagination that loads data from a
 * local collection in chunks.
 */
abstract class ClientPagingViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : UnifiedBasePagingViewModel<T>(dispatcherProvider) {

    private val _fullItemList = MutableStateFlow<List<T>>(emptyList())
    val fullItemList = _fullItemList.asStateFlow()

    private val _currentLoadedCount = MutableStateFlow(0)
    val currentLoadedCount = _currentLoadedCount.asStateFlow()

    override fun hasMoreItems(): Boolean {
        return _currentLoadedCount.value < _fullItemList.value.size
    }

    override fun loadMoreItems() {
        if (_currentLoadedCount.value >= _fullItemList.value.size) return

        val nextCount = _currentLoadedCount.value + pageSize.value
        val nextItems = _fullItemList.value.take(nextCount)
        _currentLoadedCount.value = nextCount
        setCurrentPage(nextCount / pageSize.value + (if (nextCount % pageSize.value > 0) 1 else 0))
        updateItems(nextItems, false)
    }

    /**
     * Sets the full list of items and loads the first page
     *
     * @param data The complete dataset to paginate through
     */
    protected fun setFullItemList(data: List<T>) {
        _fullItemList.value = data
        _currentLoadedCount.value = 0
        setCurrentPage(1)
        loadMoreItems()
    }

    override fun resetPaging() {
        super.resetPaging()
        _currentLoadedCount.value = 0
        _fullItemList.value = emptyList()
    }
}