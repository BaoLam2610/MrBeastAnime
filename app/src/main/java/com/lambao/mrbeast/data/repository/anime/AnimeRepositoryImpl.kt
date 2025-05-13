package com.lambao.mrbeast.data.repository.anime

import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.service.AnimeService
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeService: AnimeService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), AnimeRepository {
    override fun getAnimeFullById(id: String) = safeApiCall {
        animeService.getAnimeFullById(id)
    }
}