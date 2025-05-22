package com.lambao.mrbeast.presentation.ui.common.navigator

import com.lambao.mrbeast.domain.model.display.DisplayAnimeInfo

interface NavigatorDelegate {
    fun navigateHomeToDetail(item: DisplayAnimeInfo)
    fun navigateTopAnimeToDetail(item: DisplayAnimeInfo)
    fun navigateSeasonAnimeToDetail(item: DisplayAnimeInfo)
    fun navigateGenreToDetail(item: DisplayAnimeInfo)
    fun navigateSearchToDetail(item: DisplayAnimeInfo)
}