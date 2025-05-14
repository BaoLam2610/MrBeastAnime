package com.lambao.mrbeast.data.model.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.images.ImageRemote
import com.lambao.mrbeast.domain.model.display.DisplayAnimePictureInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimePicture(
    @SerializedName("jpg") val jpg: ImageRemote? = null,
    @SerializedName("webp") val webp: ImageRemote? = null
) : Parcelable, DisplayAnimePictureInfo {
    override fun getId() = jpg?.largeImageUrl

    override fun displayThumbnail() = jpg?.largeImageUrl ?: ""
}