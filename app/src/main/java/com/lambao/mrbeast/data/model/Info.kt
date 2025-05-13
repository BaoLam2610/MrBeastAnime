package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    @Expose @SerializedName("mal_id") val malId: Int? = null,
    @Expose @SerializedName("type") val type: String? = null,
    @Expose @SerializedName("name") val name: String? = null,
    @Expose @SerializedName("url") val url: String? = null
) : Parcelable