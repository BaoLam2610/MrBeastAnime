package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.images.ImagesRemote
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @Expose @SerializedName("url") val url: String,
    @Expose @SerializedName("username") val username: String,
    @Expose @SerializedName("images") val images: ImagesRemote? = null,
) : Parcelable
