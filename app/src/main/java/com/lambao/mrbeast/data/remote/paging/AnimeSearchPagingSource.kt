package com.lambao.mrbeast.data.remote.paging

import com.lambao.base.data.remote.paging.BasePagingSource
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.data.remote.params.search.SearchParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository

class AnimeSearchPagingSource(
    animeRepository: AnimeRepository,
    initialParams: SearchParams
) : BasePagingSource<SearchParams, Anime>(
    initialParams = initialParams,
    loadPage = { params -> animeRepository.getAnimeSearch(params) },
    getNextPageParams = { baseParams, page ->
        baseParams.copy(page = page)
    }
)