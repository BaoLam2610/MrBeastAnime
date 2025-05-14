package com.lambao.mrbeast.domain.model.display

interface DisplayAnimeFullInfo : DisplayAnimeInfo {
    fun displayType(): String
    fun displaySynopsis(): String
    fun isTvType(): Boolean
    fun isMovieType(): Boolean
    fun hasBroadcast(): Boolean
    fun shouldDisplayType(): Boolean = displayType().isNotEmpty()
    fun shouldDisplaySynopsis(): Boolean = displaySynopsis().isNotEmpty()
}