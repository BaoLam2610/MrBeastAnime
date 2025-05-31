package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    @Expose @SerializedName("score") val score: Int? = null,
    @Expose @SerializedName("votes") val votes: Int? = null,
    @Expose @SerializedName("percentage") val percentage: Double? = null,
) : Parcelable
