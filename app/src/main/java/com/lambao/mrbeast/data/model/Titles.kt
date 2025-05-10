package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Titles(
    @Expose @SerializedName("type") val type: String? = null,
    @Expose @SerializedName("title") val title: String? = null
) : Parcelable