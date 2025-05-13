package com.lambao.mrbeast.presentation.ui.common.season

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.usecase.seasons.SeasonAnimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class SeasonAnimeViewModel(
    private val getSeasonAnimeUseCase: SeasonAnimeUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), SeasonAnimeDelegate {
    private val _seasonNowList = MutableStateFlow<List<DisplaySeasonAnimeInfo>>(emptyList())

    override fun setSeasonAnimeList(data: List<DisplaySeasonAnimeInfo>) {
        launch {
            _seasonNowList.emit(data)
        }
    }

    override fun getSeasonAnimeList(): StateFlow<List<DisplaySeasonAnimeInfo>> = _seasonNowList

    override fun getSeasonAnimeUseCaseFlow(seasonsParams: SeasonsParams) =
        getSeasonAnimeUseCase.invoke(seasonsParams)

    override fun fetchSeasonAnime(seasonsParams: SeasonsParams) {
        handleData(getSeasonAnimeUseCase.invoke(seasonsParams)) {
            _seasonNowList.emit(it)
        }
    }
}