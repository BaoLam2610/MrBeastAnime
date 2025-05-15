package com.lambao.mrbeast.presentation.ui.common.view_model.top

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.top.TopAnime
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TopAnimeDelegate {
    fun setTopAnimeList(data: List<DisplayTopAnimeInfo>)
    fun getTopAnimeList(): StateFlow<List<DisplayTopAnimeInfo>>
    fun getTopAnimeUseCaseFlow(
        topParams: TopParams = TopParams(
            type = Constants.QueryParams.Type.TV,
            filter = Constants.QueryParams.Filter.AIRING,
            rating = Constants.QueryParams.Rating.PG13,
            page = 1,
            limit = 5
        )
    ): Flow<Resource<List<TopAnime>>>

    fun fetchTopAnime(
        topParams: TopParams = TopParams(
            type = Constants.QueryParams.Type.TV,
            filter = Constants.QueryParams.Filter.AIRING,
            rating = Constants.QueryParams.Rating.PG13,
            page = 1,
            limit = 5
        )
    )
}