package com.lambao.mrbeast.data.remote.paging

import com.lambao.base.data.remote.paging.BasePagingSource
import com.lambao.mrbeast.data.model.anime.AnimeEpisode
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository

class AnimeEpisodesPagingSource(
    animeRepository: AnimeRepository,
    initialParams: AnimeParams
) : BasePagingSource<AnimeParams, AnimeEpisode>(
    initialParams = initialParams,
    loadPage = { params -> animeRepository.getAnimeEpisodes(params) },
    getNextPageParams = { baseParams, page ->
        baseParams.copy(page = page)
    }
)