package com.lambao.mrbeast.data.model.anime

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.images.ImagesRemote
import com.lambao.mrbeast.domain.model.display.DisplayAnimeVideoEpisodeInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeVideoEpisode(
    @Expose @SerializedName("mal_id") val malId: Int? = null,
    @Expose @SerializedName("url") val url: String? = null,
    @Expose @SerializedName("title") val title: String? = null,
    @Expose @SerializedName("episode") val episode: String? = null,
    @Expose @SerializedName("images") val images: ImagesRemote? = null,
) : Parcelable, DisplayAnimeVideoEpisodeInfo {
    override fun getId() = malId?.toString()

    override fun displayTitle() = title ?: ""

    override fun displayThumbnail() = images?.jpg?.imageUrl ?: ""

    override fun displayEpisode() = episode ?: ""
}