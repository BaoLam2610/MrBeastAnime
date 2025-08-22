package com.lambao.base.presentation.ui.recycler_view.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import com.lambao.presentation.extension.click
import com.lambao.presentation.ui.recycler_view.BaseDiffItemCallBack
import com.lambao.base.presentation.ui.recycler_view.BaseRecyclerViewHolder

/**
 * Base adapter for RecyclerView that uses Paging3 library with DataBinding.
 *
 * @param T The type of items in the adapter
 * @param B The type of ViewDataBinding used in the ViewHolder
 * @param areItemsTheSame Function to check if two items are the same entity
 * @param areContentsTheSame Function to check if the contents of two items are the same
 * @param onItemClickListener Optional listener for item click events
 */
abstract class BasePagingAdapter<T : Any, B : ViewDataBinding>(
    areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    private val onItemClickListener: ((item: T, position: Int) -> Unit)? = null
) : PagingDataAdapter<T, BaseRecyclerViewHolder<B>>(
    BaseDiffItemCallBack(
        diffItems = areItemsTheSame,
        diffContents = areContentsTheSame
    )
) {

    /**
     * Get the layout resource ID for the given view type.
     *
     * @param viewType The view type returned by getItemViewType
     * @return The layout resource ID
     */
    @LayoutRes
    protected abstract fun getLayoutId(viewType: Int): Int

    /**
     * Bind the data to the ViewDataBinding.
     *
     * @param binding The ViewDataBinding instance
     * @param item The data item
     * @param position The position of the item in the adapter
     */
    protected abstract fun bind(binding: B, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<B> {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType),
            parent,
            false
        )
        return BaseRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<B>, position: Int) {
        val item = getItem(position) ?: return
        bind(holder.binding, item, position)
        onItemClickListener?.let { listener ->
            holder.itemView.click {
                listener.invoke(item, position)
            }
        }
    }
}