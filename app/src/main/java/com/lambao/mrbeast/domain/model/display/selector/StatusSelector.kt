package com.lambao.mrbeast.domain.model.display.selector

import com.lambao.mrbeast.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusSelector(
    override val displayText: String,
    val type: Type,
    override val id: String = type.name,
) : Selector {

    enum class Type(val param: String = "") {
        AIRING(Constants.QueryParams.Status.AIRING),
        COMPLETE(Constants.QueryParams.Status.COMPLETE),
        UPCOMING(Constants.QueryParams.Status.UPCOMING),
        DEFAULT
    }
}