package com.lambao.mrbeast.data.remote.paging

import com.lambao.base.data.remote.paging.BasePagingSource
import com.lambao.mrbeast.data.model.anime.AnimePicture
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository

class AnimePicturesPagingSource(
    animeRepository: AnimeRepository,
    initialParams: AnimeParams
) : BasePagingSource<AnimeParams, AnimePicture>(
    initialParams = initialParams,
    loadPage = { params -> animeRepository.getAnimePictures(params) },
    getNextPageParams = { baseParams, page ->
        baseParams.copy(page = page)
    }
)