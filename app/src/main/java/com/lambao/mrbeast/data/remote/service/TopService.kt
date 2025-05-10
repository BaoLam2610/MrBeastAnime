package com.lambao.mrbeast.data.remote.service

import com.lambao.base.data.remote.ApiResponse
import com.lambao.mrbeast.data.model.top_anime.TopAnime
import retrofit2.http.Field
import retrofit2.http.GET

interface TopService {
    @GET("top/anime")
    fun getTopAnime(
        @Field("type") type: String?,
        @Field("filter") filter: String?,
        @Field("rating") rating: String?,
        @Field("sfw") sfw: String?,
        @Field("limit") limit: String?,
        @Field("page") page: String?
    ): ApiResponse<TopAnime>
}