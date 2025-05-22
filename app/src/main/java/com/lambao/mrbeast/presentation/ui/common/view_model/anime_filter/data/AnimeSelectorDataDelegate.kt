package com.lambao.mrbeast.presentation.ui.common.view_model.anime_filter.data

import com.lambao.mrbeast.domain.model.display.selector.OrderBySelector
import com.lambao.mrbeast.domain.model.display.selector.RatingSelector
import com.lambao.mrbeast.domain.model.display.selector.SortSelector
import com.lambao.mrbeast.domain.model.display.selector.StatusSelector
import com.lambao.mrbeast.domain.model.display.selector.TypeSelector
import com.lambao.mrbeast.domain.model.display.selector.UnapprovedSelector
import kotlinx.coroutines.flow.StateFlow

interface AnimeSelectorDataDelegate {
    fun getAnimeUnapprovedSelector(): StateFlow<UnapprovedSelector>
    fun getAnimeTypeSelector(): StateFlow<TypeSelector>
    fun getAnimeStatusSelector(): StateFlow<StatusSelector>
    fun getAnimeRatingSelector(): StateFlow<RatingSelector>
    fun getAnimeOrderBySelector(): StateFlow<OrderBySelector>
    fun getAnimeSortSelector(): StateFlow<SortSelector>

    fun getAnimeUnapprovedSelectors(): StateFlow<List<UnapprovedSelector>>
    fun getAnimeTypeSelectors(): StateFlow<List<TypeSelector>>
    fun getAnimeStatusSelectors(): StateFlow<List<StatusSelector>>
    fun getAnimeRatingSelectors(): StateFlow<List<RatingSelector>>
    fun getAnimeOrderBySelectors(): StateFlow<List<OrderBySelector>>
    fun getAnimeSortSelectors(): StateFlow<List<SortSelector>>
}