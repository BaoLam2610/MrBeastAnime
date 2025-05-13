package com.lambao.mrbeast.data.repository.watch

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.watch.WatchEpisode
import com.lambao.mrbeast.data.model.watch.WatchPromo
import kotlinx.coroutines.flow.Flow

interface WatchRepository {
    fun getWatchPopularEpisodes(): Flow<Resource<List<WatchEpisode>>>
    fun getWatchPopularPromos(): Flow<Resource<List<WatchPromo>>>
}