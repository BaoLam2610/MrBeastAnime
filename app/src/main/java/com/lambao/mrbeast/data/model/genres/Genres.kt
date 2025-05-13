package com.lambao.mrbeast.data.model.genres

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.domain.model.display.genre.DisplayGenre
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genres(
    @Expose @SerializedName("mal_id") val malId: Int? = null,
    @Expose @SerializedName("name") val name: String? = null,
    @Expose @SerializedName("url") val url: String? = null,
    @Expose @SerializedName("count") val count: Int? = null
) : Parcelable, DisplayGenre {
    override fun displayTitle() = name ?: ""
}
