package com.lambao.mrbeast.presentation.ui.fragment.anime_filter

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.domain.model.Selectable
import com.lambao.mrbeast.domain.model.display.selector.Selector
import com.lambao.mrbeast.presentation.ui.fragment.base.filter.FilterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeFilterViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : FilterViewModel(dispatcherProvider) {

    private val _filters = MutableStateFlow<List<Selectable<Selector>>>(emptyList())
    val selectableFilters = _filters.asStateFlow()

    private val _selectedFilter = MutableStateFlow<Selector?>(null)
    val selectedFilter = _selectedFilter.asStateFlow()

    fun setFilters(filters: List<Selector>, filterSelected: Selector?) {
        _filters.value = filters.map { displayText ->
            Selectable(
                data = displayText,
                isSelected = filterSelected?.id == displayText.id
            )
        }
        _selectedFilter.value = filterSelected
    }

    fun onSelectItem(item: Selector) {
        _selectedFilter.value = item
        _filters.value = _filters.value.map { selectable ->
            Selectable(
                data = selectable.data,
                isSelected = selectable.data.id == item.id
            )
        }
    }

    fun confirmSelection() {
        // Notify parent fragment of the selected filter
    }
}