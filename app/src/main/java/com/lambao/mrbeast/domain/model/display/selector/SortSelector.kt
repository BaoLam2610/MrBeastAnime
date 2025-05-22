package com.lambao.mrbeast.domain.model.display.selector

import com.lambao.mrbeast.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class SortSelector(
    val type: Type,
    override val id: String = type.name,
    override val displayText: String
) : Selector {

    enum class Type(val param: String = "") {
        DESC(Constants.QueryParams.Sort.DESC),
        ASC(Constants.QueryParams.Sort.ASC),
        DEFAULT
    }
}