package com.lambao.mrbeast.data.remote.paging

import com.lambao.base.data.remote.paging.BasePagingSource
import com.lambao.mrbeast.data.model.anime.AnimeReview
import com.lambao.mrbeast.data.remote.params.anime.AnimeReviewParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository

class AnimeReviewsPagingSource(
    animeRepository: AnimeRepository,
    initialParams: AnimeReviewParams
) : BasePagingSource<AnimeReviewParams, AnimeReview>(
    initialParams = initialParams,
    loadPage = { params -> animeRepository.getAnimeReviews(params) },
    getNextPageParams = { baseParams, page ->
        baseParams.copy(page = page)
    }
)