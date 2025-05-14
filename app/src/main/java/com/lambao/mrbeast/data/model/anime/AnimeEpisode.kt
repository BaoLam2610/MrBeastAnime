package com.lambao.mrbeast.data.model.anime

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.base.extension.reformatDate
import com.lambao.mrbeast.domain.model.display.DisplayAnimeEpisodeInfo
import com.lambao.mrbeast.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeEpisode(
    @Expose @SerializedName("mal_id") val malId: Int? = null,
    @Expose @SerializedName("url") val url: String? = null,
    @Expose @SerializedName("title") val title: String? = null,
    @Expose @SerializedName("title_japanese") val titleJapanese: String? = null,
    @Expose @SerializedName("title_romanji") val titleRomanji: String? = null,
    @Expose @SerializedName("aired") val aired: String? = null,
    @Expose @SerializedName("score") val score: Double? = null,
    @Expose @SerializedName("filler") val filler: String? = null,
    @Expose @SerializedName("recap") val recap: String? = null,
    @Expose @SerializedName("forum_url") val forumUrl: String? = null,
    val thumbnail: String? = null,
) : Parcelable, DisplayAnimeEpisodeInfo {
    override fun getId() = malId?.toString()

    override fun displayTitle() = title ?: ""

    override fun displayScore() = score?.toString() ?: ""

    override fun displayThumbnail() = thumbnail ?: ""

    override fun displayEpisode() = getId() ?: ""

    override fun displayTimeAired() =
        aired?.reformatDate(
            Constants.DateTime.yyyyMMddTHHmmssHHmm,
            Constants.DateTime.ddMMyyyy
        ) ?: ""
}