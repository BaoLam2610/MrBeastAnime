package com.lambao.mrbeast.presentation.ui.common.watch.promos

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.watch.WatchPromo
import com.lambao.mrbeast.data.remote.params.watch.WatchParams
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WatchPopularPromosDelegate {
    fun setWatchPromoList(data: List<DisplayWatchAnimeInfo>)
    fun getWatchPromoList(): StateFlow<List<DisplayWatchAnimeInfo>>
    fun getWatchPromoUseCaseFlow(params: WatchParams = WatchParams()): Flow<Resource<List<WatchPromo>>>
    fun fetchWatchPromo(params: WatchParams = WatchParams())
}