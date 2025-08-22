package com.lambao.mrbeast.presentation.ui.common.adapter

import androidx.databinding.ViewDataBinding
import com.lambao.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.DisplayText
import com.lambao.mrbeast.domain.model.Selectable

abstract class SelectableAdapter<T : DisplayText, V : ViewDataBinding>(
    onItemClickListener: ((Selectable<T>, Int) -> Unit)? = null,
    areItemsTheSame: (Selectable<T>, Selectable<T>) -> Boolean = { old, new -> old == new },
    areContentsTheSame: (Selectable<T>, Selectable<T>) -> Boolean = { old, new -> old == new }
) : BaseDiffAdapter<Selectable<T>, V>(
    areItemsTheSame = areItemsTheSame,
    areContentsTheSame = areContentsTheSame,
    onItemClickListener = onItemClickListener
)