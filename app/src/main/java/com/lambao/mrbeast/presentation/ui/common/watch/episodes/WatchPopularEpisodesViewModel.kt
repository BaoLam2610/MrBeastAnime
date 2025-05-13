package com.lambao.mrbeast.presentation.ui.common.watch.episodes

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.watch.WatchEpisode
import com.lambao.mrbeast.data.remote.params.watch.WatchParams
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import com.lambao.mrbeast.domain.usecase.watch.GetWatchPopularEpisodesUseCase
import com.lambao.mrbeast.presentation.ui.common.watch.WatchAnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchPopularEpisodesViewModel @Inject constructor(
    getWatchEpisodeUseCase: GetWatchPopularEpisodesUseCase,
    dispatcherProvider: DispatcherProvider
) : WatchAnimeViewModel<WatchEpisode>(getWatchEpisodeUseCase, dispatcherProvider),
    WatchPopularEpisodesDelegate {
    override fun setWatchEpisodeList(data: List<DisplayWatchAnimeInfo>) {
        setWatchAnimeList(data)
    }

    override fun getWatchEpisodeList() = getWatchAnimeList()

    override fun getWatchEpisodeUseCaseFlow(params: WatchParams) =
        getWatchAnimeUseCaseFlow(params)

    override fun fetchWatchEpisode(params: WatchParams) {
        fetchWatchAnime(params)
    }
}