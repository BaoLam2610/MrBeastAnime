package com.lambao.mrbeast.data.repository.genres

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.genres.Genres
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    fun getCacheAnimeGenres(): Flow<Resource<List<Genres>>>
    fun getAnimeGenres(query: String): Flow<Resource<List<Genres>>>
    fun cacheAnimeGenres(query: String): Flow<Resource<List<Genres>>>
}