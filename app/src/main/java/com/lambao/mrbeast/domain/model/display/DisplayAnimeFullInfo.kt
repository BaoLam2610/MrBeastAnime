package com.lambao.mrbeast.domain.model.display

interface DisplayAnimeFullInfo : DisplayAnimeInfo {
    fun displayType(): String
    fun displaySynopsis(): String
    fun displayAiredFromDate(): String
    fun displayAiredToDate(): String
    fun displayStudios(): String
    fun displayProducers(): String
    fun displaySeasonYear(): String
    fun displayFavorites(): String

    fun isTvType(): Boolean
    fun isMovieType(): Boolean
    fun hasBroadcast(): Boolean

    fun shouldDisplayType(): Boolean = displayType().isNotEmpty()
    fun shouldDisplaySynopsis(): Boolean = displaySynopsis().isNotEmpty()
    fun shouldDisplayAired(): Boolean = displayAiredFromDate().isNotEmpty() && displayAiredToDate().isNotEmpty()
    fun shouldDisplayStudios(): Boolean = displayStudios().isNotEmpty()
    fun shouldDisplayProducers(): Boolean = displayProducers().isNotEmpty()
    fun shouldDisplaySeasonYear(): Boolean = displaySeasonYear().isNotEmpty()
    fun shouldDisplayFavorites(): Boolean = displayFavorites().isNotEmpty()
}