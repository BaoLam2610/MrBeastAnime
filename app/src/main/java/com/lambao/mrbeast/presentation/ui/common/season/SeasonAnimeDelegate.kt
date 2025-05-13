package com.lambao.mrbeast.presentation.ui.common.season

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SeasonAnimeDelegate {
    fun setSeasonAnimeList(data: List<DisplaySeasonAnimeInfo>)
    fun getSeasonAnimeList(): StateFlow<List<DisplaySeasonAnimeInfo>>
    fun getSeasonAnimeUseCaseFlow(seasonsParams: SeasonsParams): Flow<Resource<List<SeasonAnime>>>
    fun fetchSeasonAnime(seasonsParams: SeasonsParams)
}