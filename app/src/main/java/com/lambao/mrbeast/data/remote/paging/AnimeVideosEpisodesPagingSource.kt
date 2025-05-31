package com.lambao.mrbeast.data.remote.paging

import com.lambao.base.data.remote.paging.BasePagingSource
import com.lambao.mrbeast.data.model.anime.AnimeVideoEpisode
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository

class AnimeVideosEpisodesPagingSource(
    animeRepository: AnimeRepository,
    initialParams: AnimeParams
) : BasePagingSource<AnimeParams, AnimeVideoEpisode>(
    initialParams = initialParams,
    loadPage = { params -> animeRepository.getAnimeVideosEpisodes(params) },
    getNextPageParams = { baseParams, page ->
        baseParams.copy(page = page)
    }
)