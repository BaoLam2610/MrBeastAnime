package com.lambao.mrbeast.presentation.ui.binding

import android.view.View
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.lambao.presentation.extension.click

object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("click")
    fun View.setOnSingleClickListener(action: () -> Unit) {
        this.click { action.invoke() }
    }

    @JvmStatic
    @BindingAdapter("bgrResourceId")
    fun View.setBackgroundResourceId(@DrawableRes id: Int) {
        this.setBackgroundResource(id)
    }
}