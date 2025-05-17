package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes

import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.RemotePagingViewModel
import com.lambao.mrbeast.data.model.anime.AnimeEpisode
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeEpisodeInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeEpisodesUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeEpisodesViewModel @Inject constructor(
    private val getAnimeEpisodesUseCase: GetAnimeEpisodesUseCase,
    dispatcherProvider: DispatcherProvider
) : RemotePagingViewModel<DisplayAnimeEpisodeInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider),
    EmptyDataDelegate by EmptyDataViewModel(dispatcherProvider) {

    private val _thumbnail = MutableStateFlow("")

    private val _episodesWithThumbnails = combine(
        _thumbnail,
        items
    ) { thumbnail, episodes ->
        episodes.map {
            (it as? AnimeEpisode)?.copy(thumbnail = thumbnail)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList<DisplayAnimeEpisodeInfo>())
    val episodesWithThumbnails get() = _episodesWithThumbnails

    fun setThumbnail(thumbnail: String) {
        launch {
            _thumbnail.emit(thumbnail)
        }
    }

    fun fetchAnimeEpisodes(id: String) {
        setAnimeId(id)
        fetchData()
    }

    override fun fetchData() {
        handleDataPaging(
            getAnimeEpisodesUseCase.invoke(
                AnimeParams(
                    id = getAnimeId().value,
                    page = currentPage.value
                )
            ),
            onPaging = ::setPaging,
        ) {
            appendItems(it)
            setShowEmptyData(items.value.isEmpty())
        }
    }
}