package com.lambao.mrbeast.presentation.ui.common.season.upcoming

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonUpcomingUseCase
import com.lambao.mrbeast.presentation.ui.common.season.SeasonAnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonUpcomingViewModel @Inject constructor(
    getSeasonUpcomingUseCase: GetSeasonUpcomingUseCase,
    dispatcherProvider: DispatcherProvider
) : SeasonAnimeViewModel(getSeasonUpcomingUseCase, dispatcherProvider), SeasonUpcomingDelegate {
    override fun setSeasonUpcomingList(data: List<DisplaySeasonAnimeInfo>) {
        setSeasonAnimeList(data)
    }

    override fun getSeasonUpcomingList() = getSeasonAnimeList()

    override fun getSeasonUpcomingUseCaseFlow(seasonsParams: SeasonsParams) =
        getSeasonAnimeUseCaseFlow(seasonsParams)

    override fun fetchSeasonUpcoming(seasonsParams: SeasonsParams) {
        fetchSeasonAnime(seasonsParams)
    }
}

