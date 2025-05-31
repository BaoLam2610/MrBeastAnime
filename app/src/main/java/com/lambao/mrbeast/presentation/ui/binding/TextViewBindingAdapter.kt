package com.lambao.mrbeast.presentation.ui.binding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
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

    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("textColorResource")
    fun TextView.setTextColorResource(@ColorRes colorId: Int) {
        try {
            this.setTextColor(colorId)
        } catch (_: Exception) {
        }
    }

    @JvmStatic
    @BindingAdapter("drawableEndCompat")
    fun setDrawableEnd(textView: TextView, @DrawableRes drawableResId: Int?) {
        if (drawableResId == null || drawableResId == 0) { // 0 is often used as a default for "no drawable"
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                textView.compoundDrawablesRelative[0], // start
                textView.compoundDrawablesRelative[1], // top
                null,                                 // end
                textView.compoundDrawablesRelative[3]  // bottom
            )
            return
        }

        val drawable: Drawable? = AppCompatResources.getDrawable(textView.context, drawableResId)

        // Using setCompoundDrawablesRelativeWithIntrinsicBounds for better LTR/RTL support
        // and to use the intrinsic size of the drawable.
        // textView.compoundDrawablesRelative returns an array: [start, top, end, bottom]
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
            textView.compoundDrawablesRelative[0], // Keep existing start drawable
            textView.compoundDrawablesRelative[1], // Keep existing top drawable
            drawable,                              // Set the new end drawable
            textView.compoundDrawablesRelative[3]  // Keep existing bottom drawable
        )
    }

    @JvmStatic
    @BindingAdapter("drawableEndCompat")
    fun setDrawableEnd(textView: TextView, drawable: Drawable?) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
            textView.compoundDrawablesRelative[0],
            textView.compoundDrawablesRelative[1],
            drawable,
            textView.compoundDrawablesRelative[3]
        )
    }
}