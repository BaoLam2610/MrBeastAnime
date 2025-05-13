package com.lambao.mrbeast.presentation.ui.common.season.tv

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonNowUseCase
import com.lambao.mrbeast.presentation.ui.common.season.SeasonAnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvSeasonNowViewModel @Inject constructor(
    getSeasonNowUseCase: GetSeasonNowUseCase,
    dispatcherProvider: DispatcherProvider
) : SeasonAnimeViewModel(getSeasonNowUseCase, dispatcherProvider), TvSeasonNowDelegate {
    override fun setTvSeasonNowList(data: List<DisplaySeasonAnimeInfo>) {
        setSeasonAnimeList(data)
    }

    override fun getTvSeasonNowList() = getSeasonAnimeList()

    override fun getTvSeasonNowUseCaseFlow(seasonsParams: SeasonsParams) =
        getSeasonAnimeUseCaseFlow(seasonsParams)

    override fun fetchTvSeasonNow(seasonsParams: SeasonsParams) {
        fetchSeasonAnime(seasonsParams)
    }
}

