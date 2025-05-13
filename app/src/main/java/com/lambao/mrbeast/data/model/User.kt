package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @Expose @SerializedName("url") val url: String,
    @Expose @SerializedName("username") val username: String,
) : Parcelable
