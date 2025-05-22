package com.lambao.mrbeast.data.remote.paging

import com.lambao.base.data.remote.paging.BasePagingSource
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.data.repository.seasons.SeasonsRepository

class SeasonUpcomingPagingSource(
    seasonsRepository: SeasonsRepository,
    initialParams: SeasonsParams
) : BasePagingSource<SeasonsParams, SeasonAnime>(
    initialParams = initialParams,
    loadPage = { params -> seasonsRepository.getSeasonUpcoming(params) },
    getNextPageParams = { baseParams, page ->
        baseParams.copy(page = page)
    }
)