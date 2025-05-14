package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.images.ImagesRemote
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    @Expose @SerializedName("mal_id") val malId: Int? = null,
    @Expose @SerializedName("url") val url: String? = null,
    @Expose @SerializedName("images") val images: ImagesRemote? = null,
    @Expose @SerializedName("name") val name: String? = null,
) : Parcelable
