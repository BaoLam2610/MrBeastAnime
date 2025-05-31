package com.lambao.mrbeast.domain.model.display

import com.lambao.mrbeast.domain.model.display.reaction.ReactionItem

interface DisplayAnimeReviewInfo {
    val id: String?
    fun getReactionItems(): List<ReactionItem>
    fun displayUsername(): String
    fun displayUserAvatar(): String
    fun displayUserScoreRated(): String
    fun displayReviewDate(): String
    fun displayUserReview(): String
}