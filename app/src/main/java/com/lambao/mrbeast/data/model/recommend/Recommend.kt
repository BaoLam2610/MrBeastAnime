package com.lambao.mrbeast.data.model.recommend

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.Entry
import com.lambao.mrbeast.data.model.User
import com.lambao.mrbeast.domain.model.display.DisplayRecommendAnimeInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recommend(
    @Expose @SerializedName("mal_id") val malId: String? = null,
    @Expose @SerializedName("entry") val entry: List<Entry>? = null,
    @Expose @SerializedName("content") val content: String? = null,
    @Expose @SerializedName("date") val date: String? = null,
    @Expose @SerializedName("user") val user: User? = null
) : Parcelable, DisplayRecommendAnimeInfo {
    override fun getId() = malId
}
