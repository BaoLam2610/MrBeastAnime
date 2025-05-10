package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Broadcast(
    @SerializedName("day") val day: String? = null,
    @SerializedName("time") val time: String? = null,
    @SerializedName("timezone") val timezone: String? = null,
    @SerializedName("string") val string: String? = null
) : Parcelable
