package com.lambao.base.presentation.ui.fragment.paging

import androidx.databinding.ViewDataBinding
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.viewmodel.paging.BasePagingViewModel
import com.lambao.base.presentation.ui.viewmodel.paging.PagingDelegate

abstract class BasePagingFragment<B : ViewDataBinding, VM> :
    BaseVMFragment<B, VM>() where VM : BasePagingViewModel, VM : PagingDelegate {
    fun tryLoadMore() {
        if (viewModel.hasMoreItems()) {
            viewModel.loadMoreItems()
        }
    }
}