package com.lambao.mrbeast.data.repository.anime

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.anime.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getAnimeFullById(id: String): Flow<Resource<Anime>>
}