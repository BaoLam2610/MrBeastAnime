package com.lambao.mrbeast.domain.model.display

interface DisplayAnimeVideoEpisodeInfo : DisplayAnimeInfo {
    fun displayEpisode(): String
    fun shouldDisplayEpisode(): Boolean = displayEpisode().isNotEmpty()
}