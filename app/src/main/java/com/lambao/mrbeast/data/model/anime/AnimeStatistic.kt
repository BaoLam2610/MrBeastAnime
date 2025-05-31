package com.lambao.mrbeast.data.model.anime

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.Score
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeStatistic(
    @Expose @SerializedName("watching") val watching: Int? = null,
    @Expose @SerializedName("completed") val completed: Int? = null,
    @Expose @SerializedName("on_hold") val onHold: Int? = null,
    @Expose @SerializedName("dropped") val dropped: Int? = null,
    @Expose @SerializedName("plan_to_watch") val planToWatch: Int? = null,
    @Expose @SerializedName("total") val total: Int? = null,
    @Expose @SerializedName("scores") val scores: List<Score>? = null,
) : Parcelable
