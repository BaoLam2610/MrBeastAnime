package com.lambao.mrbeast.data.repository.genres

import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.service.GenresService
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val genresService: GenresService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), GenresRepository {
    override fun getAnimeGenres(query: String) = safeApiCall {
        genresService.getAnimeGenres(query)
    }
}