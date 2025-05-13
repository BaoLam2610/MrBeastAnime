package com.lambao.mrbeast.presentation.ui.common.season.movie

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MovieSeasonNowDelegate {
    fun setMovieSeasonNowList(data: List<DisplaySeasonAnimeInfo>)
    fun getMovieSeasonNowList(): StateFlow<List<DisplaySeasonAnimeInfo>>
    fun getMovieSeasonNowUseCaseFlow(
        seasonsParams: SeasonsParams = SeasonsParams(
            filter = Constants.QueryParams.Type.MOVIE,
            continuing = true,
            page = 1,
            limit = 10
        )
    ): Flow<Resource<List<SeasonAnime>>>

    fun fetchMovieSeasonNow(
        seasonsParams: SeasonsParams = SeasonsParams(
            filter = Constants.QueryParams.Type.MOVIE,
            continuing = true,
            page = 1,
            limit = 10
        )
    )
}