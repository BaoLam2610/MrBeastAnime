package com.lambao.mrbeast.presentation.ui.common.watch

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.remote.params.watch.WatchParams
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WatchAnimeDelegate<T : DisplayWatchAnimeInfo> {
    fun setWatchAnimeList(data: List<DisplayWatchAnimeInfo>)
    fun getWatchAnimeList(): StateFlow<List<DisplayWatchAnimeInfo>>
    fun getWatchAnimeUseCaseFlow(params: WatchParams = WatchParams()): Flow<Resource<List<T>>>
    fun fetchWatchAnime(params: WatchParams = WatchParams())
}