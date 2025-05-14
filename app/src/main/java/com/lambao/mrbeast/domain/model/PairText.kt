package com.lambao.mrbeast.domain.model

import android.os.Parcelable

interface PairText : Parcelable {
    fun getKeyText(): String
    fun getValueText(): String
    fun getKeyTextColor(): Int = com.lambao.base.R.color.text_disabled
    fun getValueTextColor(): Int = com.lambao.base.R.color.text_secondary
}