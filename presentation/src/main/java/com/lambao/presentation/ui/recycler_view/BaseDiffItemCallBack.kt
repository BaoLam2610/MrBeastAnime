package com.lambao.presentation.ui.recycler_view

import androidx.recyclerview.widget.DiffUtil

class BaseDiffItemCallBack<T : Any>(
    private val diffItems: (T, T) -> Boolean,
    private val diffContents: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = diffItems(oldItem, newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = diffContents(oldItem, newItem)
}


