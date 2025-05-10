package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateRemote(
    @Expose @SerializedName("day") val day: Int? = null,
    @Expose @SerializedName("month") val month: Int? = null,
    @Expose @SerializedName("year") val year: Int? = null
) : Parcelable