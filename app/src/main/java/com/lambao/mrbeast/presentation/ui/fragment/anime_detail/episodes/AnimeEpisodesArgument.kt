package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeEpisodesArgument(
    val id: String?,
    val thumbnail: String?
) : Parcelable
