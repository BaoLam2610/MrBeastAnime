package com.lambao.mrbeast.presentation.ui.common.view_model.anime_filter

import com.lambao.mrbeast.domain.model.Selectable
import com.lambao.mrbeast.domain.model.display.filter.AnimeFilter
import com.lambao.mrbeast.domain.model.display.selector.Selector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AnimeFilterSelectorDelegate {
    fun getSelectableAnimeFilter(): StateFlow<List<Selectable<AnimeFilter>>>
    fun onSelectAnimeFilter(item: Selectable<AnimeFilter>)
    fun onUpdateAnimeFilterSelector(item: Selectable<AnimeFilter>, selector: Selector)
    fun shouldNotifyAnimeFilter(): SharedFlow<Unit>
    fun triggerNotifyAnimeFilter()
    fun resetFilter()
    fun isSameDefaultFilter(): Boolean
}