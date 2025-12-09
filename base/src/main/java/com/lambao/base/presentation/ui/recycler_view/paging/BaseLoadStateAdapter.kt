package com.lambao.base.presentation.ui.recycler_view.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.lambao.base.extension.click
import com.lambao.base.presentation.ui.recycler_view.BaseRecyclerViewHolder

/**
 * Base adapter for handling load states in Paging 3 with DataBinding.
 *
 * @param B The type of ViewDataBinding used in the ViewHolder
 * @param onRetryClickListener Optional listener for retry button click events
 */
abstract class BaseLoadStateAdapter<B : ViewDataBinding>(
    protected val onRetryClickListener: (() -> Unit)? = null
) : LoadStateAdapter<BaseRecyclerViewHolder<B>>() {

    companion object {
        const val VIEW_TYPE_LOADING = 1
        const val VIEW_TYPE_ERROR = 2
        const val VIEW_TYPE_NOT_LOADING = 3
    }

    /**
     * Get the layout resource ID for the given load state.
     *
     * @param loadState The current load state (Loading, Error, NotLoading)
     * @return The layout resource ID
     */
    @LayoutRes
    protected abstract fun getLayoutId(loadState: LoadState): Int

    /**
     * Bind the load state to the ViewDataBinding.
     *
     * @param binding The ViewDataBinding instance
     * @param loadState The current load state
     */
    protected abstract fun bind(binding: B, loadState: LoadState)

    override fun getStateViewType(loadState: LoadState): Int {
        return when (loadState) {
            is LoadState.Loading -> VIEW_TYPE_LOADING
            is LoadState.Error -> VIEW_TYPE_ERROR
            is LoadState.NotLoading -> VIEW_TYPE_NOT_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BaseRecyclerViewHolder<B> {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(loadState),
            parent,
            false
        )
        return BaseRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<B>, loadState: LoadState) {
        bind(holder.binding, loadState)
        if (loadState is LoadState.Error) {
            onRetryClickListener?.let { listener ->
                holder.itemView.findViewById<View>(android.R.id.button1)?.click {
                    listener.invoke()
                }
            }
        }
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error
    }
}