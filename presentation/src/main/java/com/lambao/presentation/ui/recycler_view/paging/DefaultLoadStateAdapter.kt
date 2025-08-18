package com.lambao.presentation.ui.recycler_view.paging

import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import com.lambao.presentation.R
import com.lambao.presentation.databinding.LayoutFooterRetryBinding

class DefaultLoadStateAdapter(
    private val onRetryClickListener: (() -> Unit)? = null
) : BaseLoadStateAdapter<ViewDataBinding>() {

    override fun getLayoutId(loadState: LoadState): Int {
        return when (loadState) {
            is LoadState.Loading -> R.layout.layout_loading_dialog
            is LoadState.Error -> R.layout.layout_footer_retry
            else -> R.layout.layout_footer_retry
        }
    }

    override fun bind(binding: ViewDataBinding, loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                // Bind loading state (e.g., show progress bar)
            }

            is LoadState.Error -> {
                (binding as LayoutFooterRetryBinding).also {
                    it.tvMessage.text = loadState.error.localizedMessage
                    it.btnRetry.setOnClickListener {
                        onRetryClickListener?.invoke()
                    }
                }
            }

            else -> Unit
        }
    }
}


