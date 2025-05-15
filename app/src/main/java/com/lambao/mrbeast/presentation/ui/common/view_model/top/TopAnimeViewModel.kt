package com.lambao.mrbeast.presentation.ui.common.view_model.top

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.domain.usecase.GetTopAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TopAnimeViewModel @Inject constructor(
    private val getTopAnimeUseCase: GetTopAnimeUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), TopAnimeDelegate {

    private val topAnimeSlider = MutableStateFlow<List<DisplayTopAnimeInfo>>(emptyList())

    override fun setTopAnimeList(data: List<DisplayTopAnimeInfo>) {
        launch {
            topAnimeSlider.emit(data)
        }
    }

    override fun getTopAnimeList(): StateFlow<List<DisplayTopAnimeInfo>> = topAnimeSlider

    override fun getTopAnimeUseCaseFlow(topParams: TopParams) =
        getTopAnimeUseCase.invoke(topParams)


    override fun fetchTopAnime(topParams: TopParams) {
        handleData(getTopAnimeUseCase.invoke(topParams)) {
            topAnimeSlider.emit(it)
        }
    }
}