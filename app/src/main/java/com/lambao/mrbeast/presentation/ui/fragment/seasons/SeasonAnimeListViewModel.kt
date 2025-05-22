package com.lambao.mrbeast.presentation.ui.fragment.seasons

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.model.type.SeasonType
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonNowPagingUseCase
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonUpcomingPagingUseCase
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SeasonAnimeListViewModel @Inject constructor(
    private val getSeasonNowPagingUseCase: GetSeasonNowPagingUseCase,
    private val getSeasonUpcomingPagingUseCase: GetSeasonUpcomingPagingUseCase,
    dispatcherProvider: DispatcherProvider
) : AnimeListViewModel<DisplaySeasonAnimeInfo>(dispatcherProvider) {

    private val _seasonsType = MutableStateFlow(SeasonType.NOW)

    fun setSeasonsType(type: SeasonType?) {
        if (type != null) {
            _seasonsType.value = type
        }
    }

    fun getSeasonAnime() = when (_seasonsType.value) {
        SeasonType.NOW -> getSeasonNowPaginated()
        SeasonType.UPCOMING -> getSeasonUpcomingPaginated()
    }

    private fun getSeasonNowPaginated() = getPagingData {
        getSeasonNowPagingUseCase.invoke(
            SeasonsParams(
                filter = getFilter().value,
                unapproved = getUnApproved().value,
                continuing = getContinuing().value,
                sfw = getSfw().value,
                page = 1,
                limit = 20
            )
        ).map {
            it.map { item -> item as DisplaySeasonAnimeInfo }
        }.cachedIn(viewModelScope)
    }

    private fun getSeasonUpcomingPaginated() = getPagingData {
        getSeasonUpcomingPagingUseCase.invoke(
            SeasonsParams(
                filter = getFilter().value,
                unapproved = getUnApproved().value,
                continuing = getContinuing().value,
                sfw = getSfw().value,
                page = 1,
                limit = 20
            )
        ).map {
            it.map { item -> item as DisplaySeasonAnimeInfo }
        }.cachedIn(viewModelScope)
    }
}