package com.lambao.mrbeast.domain.model.display

import com.lambao.mrbeast.domain.model.PairText
import kotlinx.parcelize.Parcelize

@Parcelize
class AnimeInfoPairTextAttr(val key: String, val value: String) : PairText {
    override fun getKeyText() = key

    override fun getValueText() = value
}