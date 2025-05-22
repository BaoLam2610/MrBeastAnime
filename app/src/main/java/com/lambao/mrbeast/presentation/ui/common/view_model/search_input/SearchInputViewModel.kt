package com.lambao.mrbeast.presentation.ui.common.view_model.search_input

import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchInputViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), SearchInputDelegate {

    private val _hint = MutableStateFlow("")

    private val _value = MutableStateFlow("")

    private val _shouldShowClearValue = _value.map {
        it.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    override fun setSearchHint(hint: String) {
        _hint.value = hint
    }

    override fun setSearchValue(value: String) {
        launch { _value.emit(value) }
    }

    override fun getSearchHint(): StateFlow<String> = _hint

    override fun getSearchValue(): MutableStateFlow<String> = _value

    override fun shouldShowClearValue(): StateFlow<Boolean> = _shouldShowClearValue

    override fun clearSearchValue() {
        setSearchValue("")
    }
}