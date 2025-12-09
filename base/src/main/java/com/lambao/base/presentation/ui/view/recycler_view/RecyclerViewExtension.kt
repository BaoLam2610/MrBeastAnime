package com.lambao.base.presentation.ui.view.recycler_view

import android.content.Context
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambao.base.presentation.ui.recycler_view.paging.BaseLoadStateAdapter

fun RecyclerView.spacing(
    builder: SpacingDecorator.Builder.() -> Unit = {}
) {
    val spacing = SpacingDecorator.Builder()
        .apply(builder)
        .build()

    val removeList = mutableListOf<Int>()
    runCatching {
        repeat(this.itemDecorationCount) {
            val decoration = getItemDecorationAt(it)
            if (decoration !is SpacingDecorator) return@repeat
            val hasSetBefore = decoration == spacing
            when {
                hasSetBefore -> return
                else -> removeList.add(it)
            }
        }
    }
    removeList.forEach(::removeItemDecorationAt)
    addItemDecoration(spacing)
}

fun RecyclerView.linearSpacing(
    builder: VerticalSpacingDecorator.Companion.Builder.() -> Unit = {}
) {
    val spacing = VerticalSpacingDecorator.Companion.Builder()
        .apply(builder)
        .build()

    val removeList = mutableListOf<Int>()
    runCatching {
        repeat(this.itemDecorationCount) {
            val decoration = getItemDecorationAt(it)
            if (decoration !is VerticalSpacingDecorator) return@repeat
            val hasSetBefore = decoration == spacing
            when {
                hasSetBefore -> return
                else -> removeList.add(it)
            }
        }
    }
    removeList.forEach(::removeItemDecorationAt)
    addItemDecoration(spacing)
}

/**
 * Sets a GridLayoutManager on this RecyclerView with a custom SpanSizeLookup
 * designed to make the last item of a ConcatAdapter (typically a load state footer)
 * span across all columns.
 *
 * @param context The context required to create the GridLayoutManager.
 * @param spanCount The number of columns in the grid.
 * @param orientation The orientation of the GridLayoutManager (default is VERTICAL).
 * @param reverseLayout When set to true, layouts from end to start (default is false).
 */
fun RecyclerView.setupGridLayoutManagerWithFooterSpan(
    context: Context,
    spanCount: Int,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) {
    val gridLayoutManager = GridLayoutManager(context, spanCount, orientation, reverseLayout)
    gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val currentAdapter = this@setupGridLayoutManagerWithFooterSpan.adapter
            if (currentAdapter is ConcatAdapter && currentAdapter.itemCount > 0) {
                // Get the adapter responsible for the item at the given position
                val adapters = currentAdapter.adapters
                var itemCountSoFar = 0
                for (adapter in adapters) {
                    val adapterItemCount = adapter.itemCount
                    if (position < itemCountSoFar + adapterItemCount) {
                        // Check if the adapter for this position is BaseLoadStateAdapter
                        return if (adapter is BaseLoadStateAdapter<*> && position == currentAdapter.itemCount - 1) {
                            gridLayoutManager.spanCount // Last item from BaseLoadStateAdapter spans all columns
                        } else {
                            1 // Other items span a single column
                        }
                    }
                    itemCountSoFar += adapterItemCount
                }
            }
            return 1 // Default to single column span
        }
    }
    this.layoutManager = gridLayoutManager
}