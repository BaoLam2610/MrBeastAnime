package com.lambao.mrbeast.presentation.ui.common.view_model.watch.episodes

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.watch.WatchEpisode
import com.lambao.mrbeast.data.remote.params.watch.WatchParams
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WatchPopularEpisodesDelegate {
    fun setWatchEpisodeList(data: List<DisplayWatchAnimeInfo>)
    fun getWatchEpisodeList(): StateFlow<List<DisplayWatchAnimeInfo>>
    fun getWatchEpisodeUseCaseFlow(params: WatchParams = WatchParams()): Flow<Resource<List<WatchEpisode>>>
    fun fetchWatchEpisode(params: WatchParams = WatchParams())
}