package com.lambao.base.presentation.ui.viewmodel.paging

import com.lambao.base.data.remote.paging.Paging
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Specialized base class for remote pagination that fetches data from an API
 * with server-side pagination.
 */
abstract class RemotePagingViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : UnifiedBasePagingViewModel<T>(dispatcherProvider) {

    private val _paging = MutableStateFlow<Paging?>(null)
    val paging = _paging.asStateFlow()

    override fun hasMoreItems(): Boolean {
        if (isLoadingScreenState()) return false
        _paging.value?.hasNextPage?.let { hasNextPage ->
            return hasNextPage
        }
        return true
    }

    /**
     * Sets pagination metadata received from the API
     *
     * @param paging The pagination information from the API
     */
    fun setPaging(paging: Paging) {
        _paging.value = paging
    }

    /**
     * Adds new items to the existing list and increments the page counter
     *
     * @param data The new items to append to the list
     */
    fun appendItems(data: List<T>) {
        increaseCurrentPage()
        updateItems(data, true)
    }

    override fun resetPaging() {
        super.resetPaging()
        _paging.value = null
    }
}