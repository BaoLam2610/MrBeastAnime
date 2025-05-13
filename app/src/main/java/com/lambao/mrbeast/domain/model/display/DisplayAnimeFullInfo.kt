package com.lambao.mrbeast.domain.model.display

interface DisplayAnimeFullInfo : DisplayAnimeInfo {
    fun displaySynopsis(): String
    fun shouldDisplaySynopsis(): Boolean = displaySynopsis().isNotEmpty()
}