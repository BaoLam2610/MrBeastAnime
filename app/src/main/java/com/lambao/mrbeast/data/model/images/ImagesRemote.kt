package com.lambao.mrbeast.data.model.images

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagesRemote(
    @SerializedName("jpg") val jpg: ImageRemote? = null,
    @SerializedName("webp") val webp: ImageRemote? = null
) : Parcelable