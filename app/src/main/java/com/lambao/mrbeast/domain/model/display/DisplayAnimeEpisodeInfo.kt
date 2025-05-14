package com.lambao.mrbeast.domain.model.display

interface DisplayAnimeEpisodeInfo : DisplayAnimeVideoEpisodeInfo {
    fun displayTimeAired(): String
    fun shouldDisplayTimeAired(): Boolean = displayTimeAired().isNotEmpty()
}