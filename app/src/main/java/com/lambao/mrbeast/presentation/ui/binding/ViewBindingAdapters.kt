package com.lambao.mrbeast.presentation.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.lambao.base.extension.click

object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("click")
    fun View.setOnSingleClickListener(action: () -> Unit) {
        this.click { action.invoke() }
    }
}