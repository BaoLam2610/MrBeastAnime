package com.lambao.mrbeast.data.repository.top

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.top_anime.TopAnime
import com.lambao.mrbeast.data.remote.request.top_anime.TopAnimeRequest
import kotlinx.coroutines.flow.Flow

interface TopRepository {
    fun getTopAnime(request: TopAnimeRequest): Flow<Resource<List<TopAnime>>>
}