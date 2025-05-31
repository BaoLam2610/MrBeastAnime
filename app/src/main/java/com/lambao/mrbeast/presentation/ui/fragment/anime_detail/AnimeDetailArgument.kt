package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeDetailArgument(
    val id: String?,
    val sourceFragmentId: Int
) : Parcelable