package com.lambao.mrbeast.data.repository.genres

import com.google.gson.Gson
import com.lambao.base.data.Resource
import com.lambao.base.data.local.BaseLocalDataSource
import com.lambao.base.data.mapList
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.local.dao.GenresDao
import com.lambao.mrbeast.data.local.entity.genres.GenresEntity
import com.lambao.mrbeast.data.model.genres.toDomain
import com.lambao.mrbeast.data.model.genres.toEntity
import com.lambao.mrbeast.data.remote.service.GenresService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val genresService: GenresService,
    private val genresDao: GenresDao,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), GenresRepository {

    private val localDataSource = object : BaseLocalDataSource(dispatcherProvider) {}

    override fun getCacheAnimeGenres() = localDataSource.safeCall {
        genresDao.getGenres()
    }.map {
        it.mapList { item -> item.toDomain() }
    }

    override fun getAnimeGenres(query: String) = safeApiCall {
        genresService.getAnimeGenres(query)
    }

    override fun cacheAnimeGenres(query: String) = flow {
        safeApiCall {
            genresService.getAnimeGenres(query)
        }.collect { remoteResult ->
            when (remoteResult) {
                is Resource.Success -> {
                    remoteResult.data?.let { genres ->
                        insertCacheGenres(genres.map { it.toEntity() })
                    }
                    emit(remoteResult)
                }

                else -> emit(remoteResult)
            }
        }
    }

    private fun insertCacheGenres(genres: List<GenresEntity>) = localDataSource.safeCall {
        genresDao.insertAll(genres)
    }
}