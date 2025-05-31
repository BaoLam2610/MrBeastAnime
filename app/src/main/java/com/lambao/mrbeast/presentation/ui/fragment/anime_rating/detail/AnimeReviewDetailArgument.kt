package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.detail

import android.os.Parcelable
import com.lambao.mrbeast.data.model.anime.AnimeReview
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeReviewDetailArgument(
    val review: AnimeReview?
) : Parcelable
