package com.lambao.mrbeast.presentation.common.anime_info

import com.lambao.mrbeast.domain.model.display.DisplayAnimeInfo
import com.lambao.mrbeast.domain.model.type.InfoType

sealed class AnimeItem {
    data class Title(val text: String, val type: InfoType) : AnimeItem()
    data class Body(val data: List<DisplayAnimeInfo>) : AnimeItem()
}