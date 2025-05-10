package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Prop(
    @Expose @SerializedName("from") val from: DateRemote? = null,
    @Expose @SerializedName("to") val to: DateRemote? = null,
    @Expose @SerializedName("string") val string: String? = null
) : Parcelable