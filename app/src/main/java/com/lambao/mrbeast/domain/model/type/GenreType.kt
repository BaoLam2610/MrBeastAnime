package com.lambao.mrbeast.domain.model.type

sealed class GenreType : InfoType {
    data object PopularEpisodes : GenreType()
    data object PopularPromos : GenreType()
}