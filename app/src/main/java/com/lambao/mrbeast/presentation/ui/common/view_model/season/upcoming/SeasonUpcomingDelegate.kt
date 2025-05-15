package com.lambao.mrbeast.presentation.ui.common.view_model.season.upcoming

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SeasonUpcomingDelegate {
    fun setSeasonUpcomingList(data: List<DisplaySeasonAnimeInfo>)
    fun getSeasonUpcomingList(): StateFlow<List<DisplaySeasonAnimeInfo>>
    fun getSeasonUpcomingUseCaseFlow(
        seasonsParams: SeasonsParams = SeasonsParams(
            page = 1,
            limit = 10
        )
    ): Flow<Resource<List<SeasonAnime>>>

    fun fetchSeasonUpcoming(
        seasonsParams: SeasonsParams = SeasonsParams(
            page = 1,
            limit = 10
        )
    )
}