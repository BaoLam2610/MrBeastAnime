package com.lambao.mrbeast.presentation.ui.fragment.base.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class FilterArgument(
    open val title: String?
) : Parcelable