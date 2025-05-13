package com.lambao.mrbeast.domain.model.type

sealed class HomeType : InfoType {
    data object TopAnime : HomeType()
    data object TvSeasonNow : HomeType()
    data object MovieSeasonNow : HomeType()
    data object SeasonUpcoming : HomeType()
}