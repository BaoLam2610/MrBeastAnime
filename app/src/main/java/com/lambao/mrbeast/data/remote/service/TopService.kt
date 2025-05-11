package com.lambao.mrbeast.data.remote.service

import com.lambao.base.data.remote.ApiResponse
import com.lambao.mrbeast.data.model.top.TopAnime
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TopService {
    @GET("top/anime")
    suspend fun getTopAnime(@QueryMap queryMap: Map<String, String?>): ApiResponse<List<TopAnime>>
}