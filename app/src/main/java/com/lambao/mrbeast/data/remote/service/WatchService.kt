package com.lambao.mrbeast.data.remote.service

import com.lambao.base.data.remote.ApiResponse
import com.lambao.mrbeast.data.model.watch.WatchEpisode
import com.lambao.mrbeast.data.model.watch.WatchPromo
import retrofit2.http.GET

interface WatchService {
    @GET("watch/episodes/popular")
    suspend fun getWatchPopularEpisodes(): ApiResponse<List<WatchEpisode>>

    @GET("watch/promos/popular")
    suspend fun getWatchPopularPromos(): ApiResponse<List<WatchPromo>>
}