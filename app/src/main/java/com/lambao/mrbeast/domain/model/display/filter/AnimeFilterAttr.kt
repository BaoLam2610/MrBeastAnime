package com.lambao.mrbeast.domain.model.display.filter

import com.lambao.mrbeast.domain.model.display.selector.Selector
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeFilterAttr(
    override val id: String,
    override val displayText: String,
    override val selectors: List<Selector>,
    override var selector: Selector?
) : AnimeFilter