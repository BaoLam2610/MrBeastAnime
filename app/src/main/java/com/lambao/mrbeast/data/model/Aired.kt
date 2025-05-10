package com.lambao.mrbeast.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.Prop
import kotlinx.parcelize.Parcelize

@Parcelize
data class Aired(
    @SerializedName("from") val from: String? = null,
    @SerializedName("to") val to: String? = null,
    @SerializedName("prop") val prop: Prop? = null
) : Parcelable