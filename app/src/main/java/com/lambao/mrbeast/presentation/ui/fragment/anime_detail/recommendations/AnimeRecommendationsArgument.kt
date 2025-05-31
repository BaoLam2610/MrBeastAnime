package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.recommendations

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeRecommendationsArgument(
    val id: String?,
    val sourceFragmentId: Int
) : Parcelable
