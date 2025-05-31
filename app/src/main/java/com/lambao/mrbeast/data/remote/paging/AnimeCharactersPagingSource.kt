package com.lambao.mrbeast.data.remote.paging

import com.lambao.base.data.remote.paging.BasePagingSource
import com.lambao.mrbeast.data.model.anime.AnimeCharacter
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository

class AnimeCharactersPagingSource(
    animeRepository: AnimeRepository,
    initialParams: AnimeParams
) : BasePagingSource<AnimeParams, AnimeCharacter>(
    initialParams = initialParams,
    loadPage = { params -> animeRepository.getAnimeCharacters(params) },
    getNextPageParams = { baseParams, page ->
        baseParams.copy(page = page)
    }
)