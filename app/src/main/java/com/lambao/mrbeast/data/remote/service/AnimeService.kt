package com.lambao.mrbeast.data.remote.service

import com.lambao.base.data.remote.ApiResponse
import com.lambao.mrbeast.data.model.anime.Anime
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {
    @GET("anime/{id}/full")
    suspend fun getAnimeFullById(@Path("id") id: String): ApiResponse<Anime>
}