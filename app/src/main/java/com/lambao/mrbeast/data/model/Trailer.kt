package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trailer(
    @Expose @SerializedName("youtube_id") val youtubeId: String? = null,
    @Expose @SerializedName("url") val url: String? = null,
    @Expose @SerializedName("embed_url") val embedUrl: String? = null
) : Parcelable