package com.lambao.mrbeast.presentation.ui.fragment.anime_rating

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeRatingArgument(
    val id: String?,
    val title: String?
) : Parcelable
