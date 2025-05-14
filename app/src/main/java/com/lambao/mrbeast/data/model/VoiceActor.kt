package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoiceActor(
    @Expose @SerializedName("person") val person: Person? = null,
    @Expose @SerializedName("language") val language: String? = null
) : Parcelable
