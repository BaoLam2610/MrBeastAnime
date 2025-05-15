package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures

import android.os.Parcelable
import com.lambao.mrbeast.data.model.Trailer
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimePicturesArgument(
    val id: String?,
    val trailer: Trailer?
) : Parcelable