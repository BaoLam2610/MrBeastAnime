package com.lambao.mrbeast.presentation.ui.fragment.anime_filter

import com.lambao.mrbeast.domain.model.display.selector.Selector
import com.lambao.mrbeast.presentation.ui.fragment.base.filter.FilterArgument
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeFilterArgument(
    val filters: List<Selector>?,
    val filterSelected: Selector?,
    override val title: String?,
) : FilterArgument(title)