package com.lambao.mrbeast.data.model.watch

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.Entry
import com.lambao.mrbeast.data.model.Trailer
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchPromo(
    @Expose @SerializedName("title") val title: String? = null,
    @Expose @SerializedName("entry") val entry: Entry? = null,
    @Expose @SerializedName("trailer") val trailer: Trailer? = null,
) : Parcelable, DisplayWatchAnimeInfo {
    override fun getId() = entry?.malId?.toString()

    override fun displayTitle() = entry?.title ?: ""

    override fun displayThumbnail() = entry?.images?.jpg?.largeImageUrl ?: ""
}