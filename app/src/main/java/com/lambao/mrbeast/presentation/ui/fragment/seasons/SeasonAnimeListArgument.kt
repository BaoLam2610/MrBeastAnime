package com.lambao.mrbeast.presentation.ui.fragment.seasons

import com.lambao.mrbeast.domain.model.type.SeasonType
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListArgument
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonAnimeListArgument(
    val seasonType: SeasonType,
    override val title: String? = null,
    override val filter: String? = null,
    override val type: String? = null,
    override val unApproved: Boolean? = null,
    override val continuing: Boolean? = null,
    override val sfw: Boolean? = null,
    override val rating: String? = null,
) : AnimeListArgument(
    title,
    filter,
    type,
    unApproved,
    continuing,
    sfw,
    rating
)