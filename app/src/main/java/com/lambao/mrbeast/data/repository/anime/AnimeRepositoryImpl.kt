package com.lambao.mrbeast.data.repository.anime

import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.remote.service.AnimeService
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeService: AnimeService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), AnimeRepository {
    override fun getAnimeFullById(params: AnimeParams) = safeApiCall {
        animeService.getAnimeFullById(params.id)
    }

    override fun getAnimeEpisodes(params: AnimeParams) = safeApiCall {
        animeService.getAnimeEpisodes(params.id, params.toQueryMap())
    }

    override fun getAnimeVideosEpisodes(params: AnimeParams) = safeApiCall {
        animeService.getAnimeVideosEpisodes(params.id, params.toQueryMap())
    }

    override fun getAnimePictures(params: AnimeParams) = safeApiCall {
        animeService.getAnimePictures(params.id)
    }

    override fun getAnimeCharacters(params: AnimeParams) = safeApiCall {
        animeService.getAnimeCharacters(params.id)
    }
}