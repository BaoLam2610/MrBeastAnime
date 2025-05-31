package com.lambao.mrbeast.domain.model

import android.os.Parcelable

interface DisplayText : Parcelable {
    val id: String
    fun getTextColor(): Int = com.lambao.base.R.color.text_primary
    val displayText: String
}