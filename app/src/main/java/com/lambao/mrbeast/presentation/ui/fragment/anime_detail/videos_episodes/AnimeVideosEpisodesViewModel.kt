package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.RemotePagingViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeVideoEpisodeInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeVideosEpisodesUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeVideosEpisodesViewModel @Inject constructor(
    private val getAnimeVideosEpisodesUseCase: GetAnimeVideosEpisodesUseCase,
    dispatcherProvider: DispatcherProvider
) : RemotePagingViewModel<DisplayAnimeVideoEpisodeInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider),
    EmptyDataDelegate by EmptyDataViewModel(dispatcherProvider) {

    fun fetchAnimeVideosEpisodes(id: String) {
        setAnimeId(id)
        fetchData()
    }

    override fun fetchData() {
        handleDataPaging(
            getAnimeVideosEpisodesUseCase.invoke(
                AnimeParams(id = getAnimeId().value, page = currentPage.value)
            ),
            onPaging = ::setPaging
        ) {
            appendItems(it)
            setShowEmptyData(items.value.isEmpty())
        }
    }
}