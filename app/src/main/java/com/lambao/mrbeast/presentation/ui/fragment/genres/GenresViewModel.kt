package com.lambao.mrbeast.presentation.ui.fragment.genres

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lambao.base.data.Resource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.genres.Genres
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import com.lambao.mrbeast.domain.model.display.genre.ColorfulGenre
import com.lambao.mrbeast.domain.model.type.GenreType
import com.lambao.mrbeast.domain.usecase.genres.GetAnimeGenresUseCase
import com.lambao.mrbeast.domain.usecase.genres.GetRandomBackgroundGenreUseCase
import com.lambao.mrbeast.domain.usecase.watch.GetWatchPopularEpisodesUseCase
import com.lambao.mrbeast.domain.usecase.watch.GetWatchPopularPromosUseCase
import com.lambao.mrbeast.presentation.ui.common.adapter.anime_info.AnimeItem
import com.lambao.mrbeast.presentation.ui.common.view_model.watch.episodes.WatchPopularEpisodesDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.watch.episodes.WatchPopularEpisodesViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.watch.promos.WatchPopularPromosDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.watch.promos.WatchPopularPromosViewModel
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getAnimeGenresUseCase: GetAnimeGenresUseCase,
    getRandomBackgroundGenreUseCase: GetRandomBackgroundGenreUseCase,
    getWatchPopularEpisodesUseCase: GetWatchPopularEpisodesUseCase,
    getWatchPopularPromosUseCase: GetWatchPopularPromosUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider),
    WatchPopularEpisodesDelegate by WatchPopularEpisodesViewModel(
        getWatchPopularEpisodesUseCase,
        dispatcherProvider
    ),
    WatchPopularPromosDelegate by WatchPopularPromosViewModel(
        getWatchPopularPromosUseCase,
        dispatcherProvider
    ) {

    private val _genres = MutableStateFlow<List<Genres>>(emptyList())
    val genres = _genres.asStateFlow()

    private val _colorfulGenres = combine(
        _genres,
        getRandomBackgroundGenreUseCase.invoke()
    ) { genres, randomColors ->
        genres.mapIndexed { index, item ->
            ColorfulGenre(item, randomColors[index % randomColors.size])
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val colorfulGenres get() = _colorfulGenres

    private val _animeDisplayList = combine(
        getWatchEpisodeList(),
        getWatchPromoList()
    ) { episodes, promos ->
        buildList {
            if (promos.isNotEmpty()) {
                add(
                    AnimeItem.Title(
                        context.getString(R.string.popular_promo),
                        GenreType.PopularPromos
                    )
                )
                add(AnimeItem.Body(promos.distinctBy { it.getId() }))
            }

            if (episodes.isNotEmpty()) {
                add(
                    AnimeItem.Title(
                        context.getString(R.string.popular_episode),
                        GenreType.PopularEpisodes
                    )
                )
                add(AnimeItem.Body(episodes.distinctBy { it.getId() }))
            }

        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val animeDisplayList get() = _animeDisplayList

    fun getAnimeGenres() {
        handleData(getAnimeGenresUseCase.invoke(Constants.QueryParams.Filter.GENRES)) {
            _genres.emit(it)
        }
    }

    fun fetchAnimePopular() {
        val flows = listOf<Flow<Resource<*>>>(
            getWatchEpisodeUseCaseFlow(),
            getWatchPromoUseCaseFlow(),
        ).toTypedArray()
        handleMultiData(*flows) {
            val episodes = it[0] as List<DisplayWatchAnimeInfo>
            val promos = it[1] as List<DisplayWatchAnimeInfo>
            launch {
                setWatchEpisodeList(episodes.distinctBy { item -> item.getId() })
                setWatchPromoList(promos.distinctBy { item -> item.getId() })
            }
        }
    }
}