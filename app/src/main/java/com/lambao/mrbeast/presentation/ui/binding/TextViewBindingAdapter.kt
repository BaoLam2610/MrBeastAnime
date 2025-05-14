package com.lambao.mrbeast.presentation.ui.binding

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter

object TextViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("textColorId")
    fun TextView.setTextColorId(@ColorRes colorId: Int) {
        try {
            this.setTextColor(this.context.getColor(colorId))
        } catch (_: Exception) {
        }
    }
}