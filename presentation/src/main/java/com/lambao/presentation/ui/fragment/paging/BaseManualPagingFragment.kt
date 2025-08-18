package com.lambao.presentation.ui.fragment.paging

import androidx.databinding.ViewDataBinding
import com.lambao.presentation.ui.fragment.BaseVMFragment
import com.lambao.presentation.ui.viewmodel.paging.BaseManualPagingViewModel
import com.lambao.presentation.ui.viewmodel.paging.PagingDelegate

/**
 * Base fragment class for manual paging functionality.
 * 
 * This class extends BaseVMFragment to provide manual paging capabilities
 * for fragments that need to handle pagination manually.
 *
 * @param B The type of ViewDataBinding for this fragment
 * @param VM The type of ViewModel for this fragment (must implement BaseManualPagingViewModel and PagingDelegate)
 */
abstract class BaseManualPagingFragment<B : ViewDataBinding, VM> :
    BaseVMFragment<B, VM>() where VM : BaseManualPagingViewModel, VM : PagingDelegate {
    
    /**
     * Try to load more items if available.
     */
    fun tryLoadMore() {
        if (viewModel.hasMoreItems()) {
            viewModel.loadMoreItems()
        }
    }
}
