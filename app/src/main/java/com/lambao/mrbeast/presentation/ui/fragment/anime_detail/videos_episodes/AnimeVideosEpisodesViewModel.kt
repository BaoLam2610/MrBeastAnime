package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.BaseRemotePagingViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeVideoEpisodeInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeVideosEpisodesUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeVideosEpisodesViewModel @Inject constructor(
    private val getAnimeVideosEpisodesUseCase: GetAnimeVideosEpisodesUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseRemotePagingViewModel<DisplayAnimeVideoEpisodeInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider) {

    private val _shouldShowEmptyEpisode = MutableStateFlow(false)
    val shouldShowEmptyEpisode = _shouldShowEmptyEpisode.asStateFlow()

    fun fetchAnimeVideosEpisodes(id: String) {
        setAnimeId(id)
        fetchData()
    }

    override fun fetchData() {
        handleDataPaging(
            getAnimeVideosEpisodesUseCase.invoke(
                AnimeParams(id = getAnimeId().value, page = currentPage.value)
            ),
            onPaging = ::setPaging,
            onError = {
                _shouldShowEmptyEpisode.value = true
            }
        ) {
            setItems(it)
            _shouldShowEmptyEpisode.value = items.value.isEmpty()
        }
    }
}