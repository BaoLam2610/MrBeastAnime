package com.lambao.mrbeast.presentation.ui.fragment.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.domain.usecase.GetSeasonNowUseCase
import com.lambao.mrbeast.domain.usecase.GetSeasonUpcomingUseCase
import com.lambao.mrbeast.domain.usecase.GetTopAnimeUseCase
import com.lambao.mrbeast.presentation.common.anime_info.AnimeItem
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopAnimeUseCase: GetTopAnimeUseCase,
    private val getSeasonNowUseCase: GetSeasonNowUseCase,
    private val getSeasonUpcomingUseCase: GetSeasonUpcomingUseCase,
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _topAnimeSliders = MutableStateFlow<List<DisplayTopAnimeInfo>>(emptyList())
    val topAnimeSliders = _topAnimeSliders.asStateFlow()

    private val _tvSeasonNowList = MutableStateFlow<List<DisplaySeasonAnimeInfo>>(emptyList())

    private val _movieSeasonNowList = MutableStateFlow<List<DisplaySeasonAnimeInfo>>(emptyList())

    private val _seasonUpcomingList = MutableStateFlow<List<DisplaySeasonAnimeInfo>>(emptyList())

    private val _animeDisplayList = combine(
        _tvSeasonNowList,
        _movieSeasonNowList,
        _seasonUpcomingList
    ) { tvSeasonNowList, movieSeasonNowList, seasonUpcomingList ->
        buildList {
            if (tvSeasonNowList.isNotEmpty()) {
                add(AnimeItem.Title(context.getString(R.string.anime_tv_series)))
                add(AnimeItem.Body(tvSeasonNowList.distinctBy { it.getId() }))
            }

            if (movieSeasonNowList.isNotEmpty()) {
                add(AnimeItem.Title(context.getString(R.string.anime_movie)))
                add(AnimeItem.Body(movieSeasonNowList.distinctBy { it.getId() }))
            }

            if (seasonUpcomingList.isNotEmpty()) {
                add(AnimeItem.Title(context.getString(R.string.upcoming)))
                add(AnimeItem.Body(seasonUpcomingList.distinctBy { it.getId() }))
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val animeDisplayList get() = _animeDisplayList

    fun getTopAnimeSliders() {
        handleData(
            getTopAnimeUseCase.invoke(
                TopParams(
                    type = Constants.QueryParams.Type.TV,
                    filter = Constants.QueryParams.Filter.AIRING,
                    rating = Constants.QueryParams.Rating.PG13,
                    page = "1",
                    limit = "5"
                )
            )
        ) {
            launch {
                _topAnimeSliders.emit(it.distinctBy { it.getId() })
            }
            delay(1000)
            getTvSeasonNow()
        }
    }

    fun getTvSeasonNow() {
        handleData(
            getSeasonNowUseCase.invoke(
                SeasonsParams(
                    filter = Constants.QueryParams.Type.TV,
                    continuing = "true",
                    page = "1",
                    limit = "10"
                )
            )
        ) {
            launch {
                _tvSeasonNowList.emit(it)
            }
            delay(1000)
            getMovieSeasonNow()
        }
    }

    fun getMovieSeasonNow() {
        handleData(
            getSeasonNowUseCase.invoke(
                SeasonsParams(
                    filter = Constants.QueryParams.Type.MOVIE,
                    continuing = "true",
                    page = "1",
                    limit = "10"
                )
            )
        ) {
            launch {
                _movieSeasonNowList.emit(it)
            }
            delay(1000)
            getSeasonUpcoming()
        }
    }

    fun getSeasonUpcoming() {
        handleData(
            getSeasonUpcomingUseCase.invoke(
                SeasonsParams(
                    page = "1",
                    limit = "10"
                )
            )
        ) {
            launch {
                _seasonUpcomingList.emit(it)
            }
        }
    }
}