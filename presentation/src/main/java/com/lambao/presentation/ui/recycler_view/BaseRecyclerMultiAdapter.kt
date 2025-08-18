package com.lambao.presentation.ui.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerMultiAdapter<VH : RecyclerView.ViewHolder, T> : RecyclerView.Adapter<VH>() {
    protected val items: MutableList<T> = mutableListOf()

    override fun getItemCount(): Int = items.size

    fun submitList(data: List<T>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T = items[position]

    abstract fun onCreateView(parent: ViewGroup, viewType: Int): VH

    abstract fun onBindView(holder: VH, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = onCreateView(parent, viewType)

    override fun onBindViewHolder(holder: VH, position: Int) = onBindView(holder, items[position], position)
}


