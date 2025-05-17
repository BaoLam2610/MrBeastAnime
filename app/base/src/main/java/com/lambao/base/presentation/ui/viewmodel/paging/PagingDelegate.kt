package com.lambao.base.presentation.ui.viewmodel.paging

import kotlinx.coroutines.flow.StateFlow

interface PagingDelegate {
    val currentPage: StateFlow<Int>
    val pageSize: StateFlow<Int>
    fun loadMoreItems()
    fun hasMoreItems(): Boolean
}