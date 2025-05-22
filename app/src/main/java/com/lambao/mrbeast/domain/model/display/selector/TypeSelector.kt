package com.lambao.mrbeast.domain.model.display.selector

import com.lambao.mrbeast.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeSelector(
    val type: Type,
    override val id: String = type.name,
    override val displayText: String = type.name,
) : Selector {

    enum class Type(val param: String = "") {
        TV(Constants.QueryParams.Type.TV),
        MOVIE(Constants.QueryParams.Type.MOVIE),
        OVA(Constants.QueryParams.Type.OVA),
        SPECIAL(Constants.QueryParams.Type.SPECIAL),
        ONA(Constants.QueryParams.Type.ONA),
        MUSIC(Constants.QueryParams.Type.MUSIC),
        COMEDY(Constants.QueryParams.Type.COMEDY),
        PERSONAL_VIDEO(Constants.QueryParams.Type.PERSONAL_VIDEO),
        TV_SPECIAL(Constants.QueryParams.Type.TV_SPECIAL),
        DEFAULT
    }
}