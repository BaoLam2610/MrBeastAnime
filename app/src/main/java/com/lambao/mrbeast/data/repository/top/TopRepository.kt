package com.lambao.mrbeast.data.repository.top

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.top.TopAnime
import com.lambao.mrbeast.data.remote.params.top.TopParams
import kotlinx.coroutines.flow.Flow

interface TopRepository {
    fun getTopAnime(params: TopParams): Flow<Resource<List<TopAnime>>>
}