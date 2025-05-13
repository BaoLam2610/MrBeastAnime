package com.lambao.mrbeast.presentation.ui.common.season.movie

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonNowUseCase
import com.lambao.mrbeast.presentation.ui.common.season.SeasonAnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieSeasonNowViewModel @Inject constructor(
    getSeasonNowUseCase: GetSeasonNowUseCase,
    dispatcherProvider: DispatcherProvider
) : SeasonAnimeViewModel(getSeasonNowUseCase, dispatcherProvider), MovieSeasonNowDelegate {
    override fun setMovieSeasonNowList(data: List<DisplaySeasonAnimeInfo>) {
        setSeasonAnimeList(data)
    }

    override fun getMovieSeasonNowList() = getSeasonAnimeList()

    override fun getMovieSeasonNowUseCaseFlow(seasonsParams: SeasonsParams) =
        getSeasonAnimeUseCaseFlow(seasonsParams)

    override fun fetchMovieSeasonNow(seasonsParams: SeasonsParams) {
        fetchSeasonAnime(seasonsParams)
    }
}

