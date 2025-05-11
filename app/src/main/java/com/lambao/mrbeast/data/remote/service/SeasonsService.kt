package com.lambao.mrbeast.data.remote.service

import com.lambao.base.data.remote.ApiResponse
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SeasonsService {
    @GET("seasons/now")
    suspend fun getSeasonNow(@QueryMap queryMap: Map<String, String?>): ApiResponse<List<SeasonAnime>>

    @GET("seasons/upcoming")
    suspend fun getSeasonUpcoming(@QueryMap queryMap: Map<String, String?>): ApiResponse<List<SeasonAnime>>
}