package com.lambao.mrbeast.domain.model.display

import com.lambao.base.extension.toDate
import java.util.Date

interface DisplayAnimeFullInfo : DisplayAnimeInfo {
    fun displayType(): String
    fun displaySynopsis(): String
    fun displayAiredFromDate(): String
    fun displayAiredToDate(): String
    fun displayAiredDate(): String {
        if (displayAiredFromDate().isEmpty() && displayAiredToDate().isEmpty()) return ""
        if (displayAiredFromDate().isNotEmpty() && displayAiredToDate().isEmpty()) return displayAiredFromDate()
        if (displayAiredFromDate().isEmpty() && displayAiredToDate().isNotEmpty()) return displayAiredToDate()
        return "${displayAiredFromDate()} - ${displayAiredToDate()}"
    }

    fun displayStudios(): String
    fun displayProducers(): String
    fun displaySeasonYear(): String
    fun displayFavorites(): String

    fun getAiredFromDate(): Date?
    fun getAiredToDate(): Date?

    fun isTvType(): Boolean
    fun isMovieType(): Boolean
    fun isUpcoming(): Boolean
    fun hasBroadcast(): Boolean

    fun shouldDisplayType(): Boolean = displayType().isNotEmpty()
    fun shouldDisplaySynopsis(): Boolean = displaySynopsis().isNotEmpty()
    fun shouldDisplayAired(): Boolean = displayAiredDate().isNotEmpty()
    fun shouldDisplayStudios(): Boolean = displayStudios().isNotEmpty()
    fun shouldDisplayProducers(): Boolean = displayProducers().isNotEmpty()
    fun shouldDisplaySeasonYear(): Boolean = displaySeasonYear().isNotEmpty()
    fun shouldDisplayFavorites(): Boolean = displayFavorites().isNotEmpty()
}