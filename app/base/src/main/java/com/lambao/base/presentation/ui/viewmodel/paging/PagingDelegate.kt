package com.lambao.base.presentation.ui.viewmodel.paging

interface PagingDelegate {
    fun loadMoreItems()
    fun hasMoreItems(): Boolean
}