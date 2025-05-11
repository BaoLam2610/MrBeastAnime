package com.lambao.mrbeast.data.repository.top

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.top_anime.TopAnime
import com.lambao.mrbeast.data.remote.params.top_anime.TopAnimeParams
import kotlinx.coroutines.flow.Flow

interface TopRepository {
    fun getTopAnime(params: TopAnimeParams): Flow<Resource<List<TopAnime>>>
}