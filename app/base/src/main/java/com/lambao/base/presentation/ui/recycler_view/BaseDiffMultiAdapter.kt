package com.lambao.base.presentation.ui.recycler_view

import androidx.databinding.ViewDataBinding
import com.lambao.presentation.ui.recycler_view.BaseDiffAdapter

abstract class BaseDiffMultiAdapter<T : Any>(
    areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    onItemClickListener: ((item: T, position: Int) -> Unit)? = null
) : BaseDiffAdapter<T, ViewDataBinding>(
    areItemsTheSame = areItemsTheSame,
    areContentsTheSame = areContentsTheSame,
    onItemClickListener = onItemClickListener
) {
    protected abstract fun getViewType(item: T, position: Int): Int

    override fun getItemViewType(position: Int): Int {
        return getViewType(getItem(position), position)
    }
}