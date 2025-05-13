package com.lambao.mrbeast.presentation.ui.common.season.tv

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TvSeasonNowDelegate {
    fun setTvSeasonNowList(data: List<DisplaySeasonAnimeInfo>)
    fun getTvSeasonNowList(): StateFlow<List<DisplaySeasonAnimeInfo>>
    fun getTvSeasonNowUseCaseFlow(
        seasonsParams: SeasonsParams = SeasonsParams(
            filter = Constants.QueryParams.Type.TV,
            continuing = true,
            page = 1,
            limit = 10
        )
    ): Flow<Resource<List<SeasonAnime>>>

    fun fetchTvSeasonNow(
        seasonsParams: SeasonsParams = SeasonsParams(
            filter = Constants.QueryParams.Type.TV,
            continuing = true,
            page = 1,
            limit = 10
        )
    )
}