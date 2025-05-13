package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    @Expose @SerializedName("mal_id") val malId: Int? = null,
    @Expose @SerializedName("url") val url: String? = null,
    @Expose @SerializedName("title") val title: String? = null,
    @Expose @SerializedName("premium") val premium: Boolean? = null
) : Parcelable