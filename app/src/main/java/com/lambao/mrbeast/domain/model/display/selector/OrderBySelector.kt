package com.lambao.mrbeast.domain.model.display.selector

import com.lambao.mrbeast.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderBySelector(
    val type: Type,
    override val id: String = type.name,
    override val displayText: String,
) : Selector {

    enum class Type(val param: String = "") {
        MAL_ID(Constants.QueryParams.OrderBy.MAL_ID),
        TITLE(Constants.QueryParams.OrderBy.TITLE),
        START_DATE(Constants.QueryParams.OrderBy.START_DATE),
        END_DATE(Constants.QueryParams.OrderBy.END_DATE),
        EPISODES(Constants.QueryParams.OrderBy.EPISODES),
        SCORE(Constants.QueryParams.OrderBy.SCORE),
        SCORED_BY(Constants.QueryParams.OrderBy.SCORED_BY),
        RANK(Constants.QueryParams.OrderBy.RANK),
        POPULARITY(Constants.QueryParams.OrderBy.POPULARITY),
        MEMBERS(Constants.QueryParams.OrderBy.MEMBERS),
        FAVORITES(Constants.QueryParams.OrderBy.FAVORITES),
        DEFAULT
    }
}