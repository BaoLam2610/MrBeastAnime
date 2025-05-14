package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes

import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.AnimeEpisode
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeEpisodeInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeEpisodesViewModel @Inject constructor(
    private val getAnimeEpisodesUseCase: GetAnimeEpisodesUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _thumbnail = MutableStateFlow("")

    private val _episodes = MutableStateFlow<List<DisplayAnimeEpisodeInfo>>(emptyList())
    val episodes = _episodes.asStateFlow()

    private val _episodesWithThumbnails = combine(
        _thumbnail,
        _episodes
    ) { thumbnail, episodes ->
        episodes.map {
            (it as? AnimeEpisode)?.copy(thumbnail = thumbnail)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList<DisplayAnimeEpisodeInfo>())
    val episodesWithThumbnails get() = _episodesWithThumbnails

    private val _shouldShowEmptyEpisode = MutableStateFlow(false)
    val shouldShowEmptyEpisode = _shouldShowEmptyEpisode.asStateFlow()

    fun setThumbnail(thumbnail: String) {
        launch {
            _thumbnail.emit(thumbnail)
        }
    }

    fun fetchAnimeEpisodes(id: String) {
        handleDataNoLoading(
            getAnimeEpisodesUseCase.invoke(AnimeParams(id = id)),
            onError = {
                _shouldShowEmptyEpisode.value = true
            }
        ) {
            _episodes.emit(it)
            _shouldShowEmptyEpisode.value = it.isEmpty()
        }
    }
}