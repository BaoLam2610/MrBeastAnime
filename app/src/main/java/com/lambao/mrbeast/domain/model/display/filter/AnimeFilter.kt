package com.lambao.mrbeast.domain.model.display.filter

import com.lambao.mrbeast.domain.model.DisplayText
import com.lambao.mrbeast.domain.model.display.selector.Selector

interface AnimeFilter : DisplayText {
    val selectors: List<Selector>
    var selector: Selector?
}