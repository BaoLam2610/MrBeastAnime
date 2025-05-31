package com.lambao.mrbeast.data.repository.seasons

import androidx.paging.PagingData
import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.data.remote.params.top.TopParams
import kotlinx.coroutines.flow.Flow

interface SeasonsRepository {
    fun getSeasonNow(params: SeasonsParams): Flow<Resource<List<SeasonAnime>>>
    fun getSeasonUpcoming(params: SeasonsParams): Flow<Resource<List<SeasonAnime>>>
    fun getSeasonNowPaginated(params: SeasonsParams): Flow<PagingData<SeasonAnime>>
    fun getSeasonUpcomingPaginated(params: SeasonsParams): Flow<PagingData<SeasonAnime>>
}