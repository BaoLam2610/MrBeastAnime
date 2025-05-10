package com.lambao.mrbeast.data.repository.top

import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.request.top_anime.TopAnimeRequest
import com.lambao.mrbeast.data.remote.service.TopService
import javax.inject.Inject

class TopRepositoryImpl @Inject constructor(
    private val topService: TopService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), TopRepository {
    override fun getTopAnime(request: TopAnimeRequest) = safeApiCall {
        topService.getTopAnime(request.toQueryMap())
    }
}