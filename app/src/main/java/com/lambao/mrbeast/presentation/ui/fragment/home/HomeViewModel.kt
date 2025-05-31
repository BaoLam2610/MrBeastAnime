package com.lambao.mrbeast.presentation.ui.fragment.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lambao.base.data.Resource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.domain.model.type.HomeType
import com.lambao.mrbeast.domain.usecase.top.GetTopAnimeUseCase
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonNowUseCase
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonUpcomingUseCase
import com.lambao.mrbeast.presentation.ui.common.adapter.anime_info.AnimeItem
import com.lambao.mrbeast.presentation.ui.common.view_model.data_handler.DataHandlerDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.data_handler.DataHandlerViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.season.movie.MovieSeasonNowDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.season.movie.MovieSeasonNowViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.season.tv.TvSeasonNowDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.season.tv.TvSeasonNowViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.season.upcoming.SeasonUpcomingDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.season.upcoming.SeasonUpcomingViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.top.TopAnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.top.TopAnimeViewModel
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTopAnimeUseCase: GetTopAnimeUseCase,
    getSeasonNowUseCase: GetSeasonNowUseCase,
    getSeasonUpcomingUseCase: GetSeasonUpcomingUseCase,
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider),
    DataHandlerDelegate by DataHandlerViewModel(dispatcherProvider),
    TopAnimeDelegate by TopAnimeViewModel(getTopAnimeUseCase, dispatcherProvider),
    TvSeasonNowDelegate by TvSeasonNowViewModel(getSeasonNowUseCase, dispatcherProvider),
    MovieSeasonNowDelegate by MovieSeasonNowViewModel(getSeasonNowUseCase, dispatcherProvider),
    SeasonUpcomingDelegate by SeasonUpcomingViewModel(
        getSeasonUpcomingUseCase,
        dispatcherProvider
    ) {

    private val _animeDisplayList = combine(
        getTvSeasonNowList(),
        getMovieSeasonNowList(),
        getSeasonUpcomingList()
    ) { tvSeasonNowList, movieSeasonNowList, seasonUpcomingList ->
        buildList {
            if (tvSeasonNowList.isNotEmpty()) {
                add(
                    AnimeItem.Title(
                        context.getString(R.string.anime_tv_series),
                        HomeType.TvSeasonNow
                    )
                )
                add(AnimeItem.Body(tvSeasonNowList.distinctBy { it.getId() }))
            }

            if (movieSeasonNowList.isNotEmpty()) {
                add(
                    AnimeItem.Title(
                        context.getString(R.string.anime_movie),
                        HomeType.MovieSeasonNow
                    )
                )
                add(AnimeItem.Body(movieSeasonNowList.distinctBy { it.getId() }))
            }

            if (seasonUpcomingList.isNotEmpty()) {
                add(
                    AnimeItem.Title(
                        context.getString(R.string.upcoming),
                        HomeType.SeasonUpcoming
                    )
                )
                add(AnimeItem.Body(seasonUpcomingList.distinctBy { it.getId() }))
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val animeDisplayList get() = _animeDisplayList

    fun fetchAnimeData() {
        val flows = listOf<Flow<Resource<*>>>(
            getTopAnimeUseCaseFlow(),
            getTvSeasonNowUseCaseFlow(),
            getMovieSeasonNowUseCaseFlow()
        ).toTypedArray()
        handleMultiData(*flows) {
            val topAnime = it[0] as List<DisplayTopAnimeInfo>
            val tvSeasonNowList = it[1] as List<DisplaySeasonAnimeInfo>
            val movieSeasonNowList = it[2] as List<DisplaySeasonAnimeInfo>
            launch {
                setTopAnimeList(topAnime.distinctBy { item -> item.getId() })
                setTvSeasonNowList(tvSeasonNowList.distinctBy { item -> item.getId() })
                setMovieSeasonNowList(movieSeasonNowList.distinctBy { item -> item.getId() })
            }
        }
    }
}