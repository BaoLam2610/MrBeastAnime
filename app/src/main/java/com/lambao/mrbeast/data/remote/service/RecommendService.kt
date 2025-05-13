package com.lambao.mrbeast.data.remote.service

import com.lambao.base.data.remote.ApiResponse
import com.lambao.mrbeast.data.model.watch.WatchEpisode
import retrofit2.http.GET

interface RecommendService {
    @GET("recommendations/anime")
    suspend fun get(): ApiResponse<List<WatchEpisode>>
}