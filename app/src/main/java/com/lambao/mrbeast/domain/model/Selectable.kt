package com.lambao.mrbeast.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Selectable<T : DisplayText>(
    var isSelected: Boolean = false,
    val data: T
) : Parcelable