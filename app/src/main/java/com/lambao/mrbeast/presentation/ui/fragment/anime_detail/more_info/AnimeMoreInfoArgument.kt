package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.more_info

import android.os.Parcelable
import com.lambao.mrbeast.data.model.anime.Anime
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeMoreInfoArgument(
    val anime : Anime?
) : Parcelable