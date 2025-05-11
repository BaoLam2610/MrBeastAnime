package com.lambao.mrbeast.data.repository.seasons

import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.data.remote.service.SeasonsService
import javax.inject.Inject

class SeasonsRepositoryImpl @Inject constructor(
    private val seasonsService: SeasonsService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), SeasonsRepository {
    override fun getSeasonNow(params: SeasonsParams) = safeApiCall {
        seasonsService.getSeasonNow(params.toQueryMap())
    }

    override fun getSeasonUpcoming(params: SeasonsParams) = safeApiCall {
        seasonsService.getSeasonUpcoming(params.toQueryMap())
    }
}