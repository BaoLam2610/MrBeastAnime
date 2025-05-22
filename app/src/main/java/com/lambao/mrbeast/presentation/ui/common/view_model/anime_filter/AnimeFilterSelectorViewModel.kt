package com.lambao.mrbeast.presentation.ui.common.view_model.anime_filter

import android.content.Context
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.domain.model.Selectable
import com.lambao.mrbeast.domain.model.display.filter.AnimeFilter
import com.lambao.mrbeast.domain.model.display.filter.AnimeFilterAttr
import com.lambao.mrbeast.domain.model.display.selector.Selector
import com.lambao.mrbeast.presentation.ui.common.view_model.anime_filter.data.AnimeSelectorDataDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime_filter.data.AnimeSelectorDataViewModel
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeFilterSelectorViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), AnimeFilterSelectorDelegate,
    AnimeSelectorDataDelegate by AnimeSelectorDataViewModel(context, dispatcherProvider) {

    private val _shouldNotifyAnimeFilter = MutableSharedFlow<Unit>()

    private val _defaultSelectableFilters: List<Selectable<AnimeFilter>>
        get() = listOf(
            AnimeFilterAttr(
                id = R.string.type.toString(),
                displayText = context.getString(R.string.type),
                selectors = getAnimeTypeSelectors().value,
                selector = getAnimeTypeSelector().value
            ),
            AnimeFilterAttr(
                id = R.string.status.toString(),
                displayText = context.getString(R.string.status),
                selectors = getAnimeStatusSelectors().value,
                selector = getAnimeStatusSelector().value
            ),
            AnimeFilterAttr(
                id = R.string.teen.toString(),
                displayText = context.getString(R.string.teen),
                selectors = getAnimeRatingSelectors().value,
                selector = getAnimeRatingSelector().value
            ),
            AnimeFilterAttr(
                id = R.string.order_by.toString(),
                displayText = context.getString(R.string.order_by),
                selectors = getAnimeOrderBySelectors().value,
                selector = getAnimeStatusSelector().value
            ),
            AnimeFilterAttr(
                id = R.string.sort.toString(),
                displayText = context.getString(R.string.sort),
                selectors = getAnimeSortSelectors().value,
                selector = getAnimeSortSelector().value
            ),
            AnimeFilterAttr(
                id = R.string.reset.toString(),
                displayText = context.getString(R.string.reset),
                selectors = emptyList(),
                selector = null
            )
        ).map {
            Selectable(
                isSelected = false,
                data = it
            )
        }

    private val _selectableFilters = MutableStateFlow(_defaultSelectableFilters)

    override fun getSelectableAnimeFilter(): StateFlow<List<Selectable<AnimeFilter>>> =
        _selectableFilters

    override fun onSelectAnimeFilter(item: Selectable<AnimeFilter>) {
        item.isSelected = !item.isSelected
        _selectableFilters.value = _selectableFilters.value.map { filter ->
            if (filter.data.id == item.data.id) {
                item
            } else {
                filter
            }
        }
        triggerNotifyAnimeFilter()
    }

    override fun onUpdateAnimeFilterSelector(
        item: Selectable<AnimeFilter>,
        selector: Selector
    ) {
        _selectableFilters.value = _selectableFilters.value.map { filter ->
            if (filter.data.id == item.data.id) {
                item.isSelected = selector.id != Constants.Key.DEFAULT
                item.data.selector = selector
                item
            } else {
                filter
            }
        }
        triggerNotifyAnimeFilter()
    }

    override fun shouldNotifyAnimeFilter(): SharedFlow<Unit> = _shouldNotifyAnimeFilter

    override fun triggerNotifyAnimeFilter() {
        launch { _shouldNotifyAnimeFilter.emit(Unit) }
    }

    override fun resetFilter() {
        _selectableFilters.value = _defaultSelectableFilters
        triggerNotifyAnimeFilter()
    }

    override fun isSameDefaultFilter(): Boolean =
        _selectableFilters.value == _defaultSelectableFilters
}