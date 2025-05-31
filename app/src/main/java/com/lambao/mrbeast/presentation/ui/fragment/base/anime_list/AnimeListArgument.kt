package com.lambao.mrbeast.presentation.ui.fragment.base.anime_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class AnimeListArgument(
    open val title: String? = null,
    open val filter: String? = null,
    open val type: String? = null,
    open val unApproved: Boolean? = null,
    open val continuing: Boolean? = null,
    open val sfw: Boolean? = null,
    open val rating: String? = null
) : Parcelable