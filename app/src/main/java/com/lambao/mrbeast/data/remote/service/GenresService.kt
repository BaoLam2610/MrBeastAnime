package com.lambao.mrbeast.data.remote.service

import com.lambao.base.data.remote.ApiResponse
import com.lambao.mrbeast.data.model.genres.Genres
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {
    @GET("genres/anime")
    suspend fun getAnimeGenres(@Query("filter") query: String): ApiResponse<List<Genres>>
}