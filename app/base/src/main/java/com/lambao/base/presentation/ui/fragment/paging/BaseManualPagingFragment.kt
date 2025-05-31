package com.lambao.base.presentation.ui.fragment.paging

import androidx.databinding.ViewDataBinding
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.viewmodel.paging.BaseManualPagingViewModel
import com.lambao.base.presentation.ui.viewmodel.paging.PagingDelegate

abstract class BaseManualPagingFragment<B : ViewDataBinding, VM> :
    BaseVMFragment<B, VM>() where VM : BaseManualPagingViewModel, VM : PagingDelegate {
    fun tryLoadMore() {
        if (viewModel.hasMoreItems()) {
            viewModel.loadMoreItems()
        }
    }

    override fun showLoading() = Unit

    override fun hideLoading() = Unit
}