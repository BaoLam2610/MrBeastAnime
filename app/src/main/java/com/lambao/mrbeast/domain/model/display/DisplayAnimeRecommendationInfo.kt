package com.lambao.mrbeast.domain.model.display

interface DisplayAnimeRecommendationInfo : DisplayAnimeInfo {
    fun displayVotes(): String
    fun shouldDisplayVotes(): Boolean = displayVotes().isNotEmpty()
}