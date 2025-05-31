package com.lambao.mrbeast.data.remote.service

import com.lambao.base.data.remote.ApiResponse
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.data.model.anime.AnimeCharacter
import com.lambao.mrbeast.data.model.anime.AnimeEpisode
import com.lambao.mrbeast.data.model.anime.AnimePicture
import com.lambao.mrbeast.data.model.anime.AnimeRecommendation
import com.lambao.mrbeast.data.model.anime.AnimeReview
import com.lambao.mrbeast.data.model.anime.AnimeStatistic
import com.lambao.mrbeast.data.model.anime.AnimeVideoEpisode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface AnimeService {
    @GET("anime/{id}/full")
    suspend fun getAnimeFullById(
        @Path("id") id: String
    ): ApiResponse<Anime>

    @GET("anime/{id}/episodes")
    suspend fun getAnimeEpisodes(
        @Path("id") id: String,
        @QueryMap queryMap: Map<String, String?>
    ): ApiResponse<List<AnimeEpisode>>

    @GET("anime/{id}/videos/episodes")
    suspend fun getAnimeVideosEpisodes(
        @Path("id") id: String,
        @QueryMap queryMap: Map<String, String?>
    ): ApiResponse<List<AnimeVideoEpisode>>

    @GET("anime/{id}/pictures")
    suspend fun getAnimePictures(
        @Path("id") id: String
    ): ApiResponse<List<AnimePicture>>

    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(
        @Path("id") id: String
    ): ApiResponse<List<AnimeCharacter>>

    @GET("anime/{id}/recommendations")
    suspend fun getAnimeRecommendations(
        @Path("id") id: String
    ): ApiResponse<List<AnimeRecommendation>>

    @GET("anime/{id}/reviews")
    suspend fun getAnimeReviews(
        @Path("id") id: String,
        @QueryMap queryMap: Map<String, String?>
    ): ApiResponse<List<AnimeReview>>

    @GET("anime/{id}/statistics")
    suspend fun getAnimeStatistics(
        @Path("id") id: String
    ): ApiResponse<AnimeStatistic>

    @GET("anime")
    suspend fun getAnimeSearch(
        @QueryMap queryMap: Map<String, String?>
    ): ApiResponse<List<Anime>>
}