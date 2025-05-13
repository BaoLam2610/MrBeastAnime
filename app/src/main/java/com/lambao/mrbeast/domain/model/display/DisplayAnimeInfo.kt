package com.lambao.mrbeast.domain.model.display

interface DisplayAnimeInfo {
    fun getId(): String?
    fun displayTitle(): String = ""
    fun displayGenres(): String = ""
    fun displayScore(): String = ""
    fun displayThumbnail(): String = ""

    fun shouldDisplayTitle() = displayTitle().isNotEmpty()
    fun shouldDisplayGenres() = displayGenres().isNotEmpty()
    fun shouldDisplayScore() = displayScore().isNotEmpty()
    fun shouldDisplayThumbnail(): Boolean = true
}