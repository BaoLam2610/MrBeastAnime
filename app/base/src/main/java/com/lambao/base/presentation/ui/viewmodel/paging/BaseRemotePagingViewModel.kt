package com.lambao.base.presentation.ui.viewmodel.paging

import com.lambao.base.data.remote.paging.Paging
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseRemotePagingViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : BasePagingViewModel(dispatcherProvider), PagingDelegate {

    private val _items = MutableStateFlow<List<T>>(emptyList())
    val items = _items.asStateFlow()

    private val _paging = MutableStateFlow<Paging?>(null)
    val paging = _paging.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()

    private val _pageSize = MutableStateFlow(5)
    protected val pageSize = _pageSize.asStateFlow()

    abstract fun fetchData()

    override fun loadMoreItems() {
        fetchData()
    }

    override fun hasMoreItems(): Boolean {
        if (isLoadingScreenState()) return false
        _paging.value?.hasNextPage?.let { hasNextPage ->
            return hasNextPage
        }
        return true
    }

    fun setPaging(paging: Paging) {
        _paging.value = paging
    }

    private fun increaseCurrentPage() {
        _currentPage.value += 1
    }

    fun setItems(data: List<T>) {
        increaseCurrentPage()
        val newList = _items.value + data
        _items.value = newList
    }

    fun resetPaging() {
        _currentPage.value = 1
        _items.value = emptyList()
        _paging.value = null
    }

    fun setPageSize(size: Int) {
        _pageSize.value = size
    }
}