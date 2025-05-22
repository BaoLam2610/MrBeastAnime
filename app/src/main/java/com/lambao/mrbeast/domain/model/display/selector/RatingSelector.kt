package com.lambao.mrbeast.domain.model.display.selector

import com.lambao.mrbeast.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingSelector(
    val type: Type,
    override val id: String = type.name,
    override val displayText: String,
) : Selector {

    enum class Type(val param: String = "") {
        G(Constants.QueryParams.Rating.G),
        PG(Constants.QueryParams.Rating.PG),
        PG13(Constants.QueryParams.Rating.PG13),
        R17(Constants.QueryParams.Rating.R17),
        R(Constants.QueryParams.Rating.R),
        RX(Constants.QueryParams.Rating.RX),
        DEFAULT
    }
}