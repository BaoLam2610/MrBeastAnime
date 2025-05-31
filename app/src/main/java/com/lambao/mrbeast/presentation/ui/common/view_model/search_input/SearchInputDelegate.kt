package com.lambao.mrbeast.presentation.ui.common.view_model.search_input

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SearchInputDelegate {
    fun setSearchHint(hint: String)
    fun setSearchValue(value: String)
    fun getSearchHint(): StateFlow<String>
    fun getSearchValue(): MutableStateFlow<String>
    fun shouldShowClearValue(): StateFlow<Boolean>
    fun clearSearchValue()
}