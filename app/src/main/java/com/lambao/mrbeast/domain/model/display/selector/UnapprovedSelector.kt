package com.lambao.mrbeast.domain.model.display.selector

import kotlinx.parcelize.Parcelize

@Parcelize
data class UnapprovedSelector(
    val type: Type,
    override val id: String = type.name,
    override val displayText: String
) : Selector {

    enum class Type(val param: Boolean? = null) {
        YES(true),
        NO(false),
        DEFAULT
    }
}