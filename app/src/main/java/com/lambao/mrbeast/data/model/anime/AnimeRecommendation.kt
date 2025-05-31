package com.lambao.mrbeast.data.model.anime

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.Entry
import com.lambao.mrbeast.domain.model.display.DisplayAnimeRecommendationInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeRecommendation(
    @Expose @SerializedName("entry") val entry: Entry?,
    @Expose @SerializedName("url") val url: String?,
    @Expose @SerializedName("votes") val votes: Int?
) : Parcelable, DisplayAnimeRecommendationInfo {
    override fun getId() = entry?.malId?.toString()

    override fun displayTitle() = entry?.title ?: ""

    override fun displayThumbnail() = entry?.images?.jpg?.largeImageUrl ?: ""

    override fun displayVotes() = votes?.toString() ?: ""
}