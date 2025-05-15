package com.lambao.mrbeast.presentation.ui.common.view_model.anime

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), AnimeDelegate {

    private val _animeId = MutableStateFlow("")

    override fun setAnimeId(id: String) {
        _animeId.value = id
    }

    override fun getAnimeId(): StateFlow<String> = _animeId
}