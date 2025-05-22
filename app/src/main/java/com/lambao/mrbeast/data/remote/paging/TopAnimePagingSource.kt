package com.lambao.mrbeast.data.remote.paging

import com.lambao.base.data.remote.paging.BasePagingSource
import com.lambao.mrbeast.data.model.top.TopAnime
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.data.repository.top.TopRepository

class TopAnimePagingSource(
    topRepository: TopRepository,
    initialParams: TopParams
) : BasePagingSource<TopParams, TopAnime>(
    initialParams = initialParams,
    loadPage = { params -> topRepository.getTopAnime(params) },
    getNextPageParams = { baseParams, page ->
        baseParams.copy(page = page)
    }
)