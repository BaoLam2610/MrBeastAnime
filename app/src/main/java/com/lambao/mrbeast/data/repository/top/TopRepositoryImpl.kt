package com.lambao.mrbeast.data.repository.top

import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.top_anime.TopAnimeParams
import com.lambao.mrbeast.data.remote.service.TopService
import javax.inject.Inject

class TopRepositoryImpl @Inject constructor(
    private val topService: TopService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), TopRepository {
    override fun getTopAnime(params: TopAnimeParams) = safeApiCall {
        topService.getTopAnime(params.toQueryMap())
    }
}