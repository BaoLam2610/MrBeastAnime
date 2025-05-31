package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reaction(
    @Expose @SerializedName("overall") val overall: Int? = null,
    @Expose @SerializedName("nice") val nice: Int? = null,
    @Expose @SerializedName("love_it") val loveIt: Int? = null,
    @Expose @SerializedName("funny") val funny: Int? = null,
    @Expose @SerializedName("confusing") val confusing: Int? = null,
    @Expose @SerializedName("informative") val informative: Int? = null,
    @Expose @SerializedName("well_written") val wellWritten: Int? = null,
    @Expose @SerializedName("creative") val creative: Int? = null,
) : Parcelable
