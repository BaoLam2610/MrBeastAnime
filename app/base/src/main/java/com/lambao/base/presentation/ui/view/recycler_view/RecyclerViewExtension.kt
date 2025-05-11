package com.lambao.base.presentation.ui.view.recycler_view

import androidx.recyclerview.widget.RecyclerView

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