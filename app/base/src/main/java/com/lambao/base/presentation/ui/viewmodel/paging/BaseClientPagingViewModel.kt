package com.lambao.base.presentation.ui.viewmodel.paging

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseClientPagingViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : BasePagingViewModel(dispatcherProvider), PagingDelegate {

    private val _items = MutableStateFlow<List<T>>(emptyList())
    val items = _items.asStateFlow()

    private val _fullItemList = MutableStateFlow<List<T>>(emptyList())
    val fullItemList = _fullItemList.asStateFlow()

    private val _currentLoadedCount = MutableStateFlow(0)
    val currentLoadedCount = _currentLoadedCount.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()

    private val _pageSize = MutableStateFlow(20)
    protected val pageSize = _pageSize.asStateFlow()

    abstract fun fetchData()

    override fun loadMoreItems() {
        if (_currentLoadedCount.value >= _fullItemList.value.size) return

        val nextCount = _currentLoadedCount.value + _pageSize.value
        val nextItems = _fullItemList.value.take(nextCount)
        _currentLoadedCount.value = nextCount
        _currentPage.value = nextCount / _pageSize.value + 1
        _items.value = nextItems
    }

    override fun hasMoreItems(): Boolean {
        return _currentLoadedCount.value < _fullItemList.value.size
    }

    protected fun setFullItemList(data: List<T>) {
        _fullItemList.value = data
        _currentLoadedCount.value = 0
        _currentPage.value = 1
        loadMoreItems()
    }

    fun resetPaging() {
        _currentLoadedCount.value = 0
        _currentPage.value = 1
        _items.value = emptyList()
        _fullItemList.value = emptyList()
    }

    fun setPageSize(size: Int) {
        _pageSize.value = size
    }
}