package com.lambao.mrbeast.data.repository.watch

import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.service.WatchService
import javax.inject.Inject

class WatchRepositoryImpl @Inject constructor(
    private val watchService: WatchService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), WatchRepository {
    override fun getWatchPopularEpisodes() = safeApiCall {
        watchService.getWatchPopularEpisodes()
    }

    override fun getWatchPopularPromos() = safeApiCall {
        watchService.getWatchPopularPromos()
    }
}