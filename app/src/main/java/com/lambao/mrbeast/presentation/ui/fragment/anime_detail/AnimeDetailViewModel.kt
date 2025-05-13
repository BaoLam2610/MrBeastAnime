package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.domain.model.display.DisplayAnimeFullInfo
import com.lambao.mrbeast.domain.usecase.GetAnimeFullByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val getAnimeFullByIdUseCase: GetAnimeFullByIdUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _anime = MutableStateFlow<DisplayAnimeFullInfo?>(null)
    val anime = _anime.asStateFlow()

    fun fetchAnimeInfo(id: String) {
        handleData(getAnimeFullByIdUseCase.invoke(id)) {
            _anime.emit(it)
        }
    }
}