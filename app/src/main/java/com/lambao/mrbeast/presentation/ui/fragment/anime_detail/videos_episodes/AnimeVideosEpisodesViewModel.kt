package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeVideoEpisodeInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeVideosEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeVideosEpisodesViewModel @Inject constructor(
    private val getAnimeVideosEpisodesUseCase: GetAnimeVideosEpisodesUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _episodes = MutableStateFlow<List<DisplayAnimeVideoEpisodeInfo>>(emptyList())
    val episodes = _episodes.asStateFlow()

    private val _shouldShowEmptyEpisode = MutableStateFlow(false)
    val shouldShowEmptyEpisode = _shouldShowEmptyEpisode.asStateFlow()

    fun fetchAnimeVideosEpisodes(id: String) {
        handleDataNoLoading(
            getAnimeVideosEpisodesUseCase.invoke(AnimeParams(id = id)),
            onError = {
                _shouldShowEmptyEpisode.value = true
            }
        ) {
            _episodes.emit(it)
            _shouldShowEmptyEpisode.value = it.isEmpty()
        }
    }
}