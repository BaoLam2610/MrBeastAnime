package com.lambao.mrbeast.data.model.watch

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.Entry
import com.lambao.mrbeast.data.model.Episode
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchEpisode(
    @Expose @SerializedName("entry") val entry: Entry? = null,
    @Expose @SerializedName("episodes") val episodes: List<Episode>? = null,
    @Expose @SerializedName("region_locked") val regionLocked: Boolean? = null,
) : Parcelable, DisplayWatchAnimeInfo {
    override fun getId() = entry?.malId?.toString()

    override fun displayTitle() = entry?.title ?: ""

    override fun displayThumbnail() = entry?.images?.jpg?.largeImageUrl ?: ""
}