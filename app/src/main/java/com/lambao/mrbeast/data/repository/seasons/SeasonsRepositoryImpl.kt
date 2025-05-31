package com.lambao.mrbeast.data.repository.seasons

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.paging.SeasonNowPagingSource
import com.lambao.mrbeast.data.remote.paging.SeasonUpcomingPagingSource
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

    override fun getSeasonNowPaginated(params: SeasonsParams) = Pager(
        config = PagingConfig(
            pageSize = params.limit,
            initialLoadSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SeasonNowPagingSource(this, params)
        }
    ).flow

    override fun getSeasonUpcomingPaginated(params: SeasonsParams) = Pager(
        config = PagingConfig(
            pageSize = params.limit,
            initialLoadSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SeasonUpcomingPagingSource(this, params)
        }
    ).flow
}