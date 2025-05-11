package com.lambao.mrbeast.presentation.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.lambao.base.extension.loadBlurImage

object ImageViewBindingAdapters {
    /**
     * Loads an image from a URL into an ImageView using Glide with customizable options.
     *
     * @param url The URL of the image to load.
     * @param placeholderResId Resource ID of the placeholder image (optional).
     * @param errorResId Resource ID of the error image (optional).
     */
    @JvmStatic
    @BindingAdapter(
        "imageUrl",
        "placeholderResId",
        "errorResId",
        requireAll = false
    )
    fun ImageView.loadImageUrl(
        url: String?,
        placeholderResId: Int? = null,
        errorResId: Int? = null
    ) {
        if (url.isNullOrBlank()) {
            placeholderResId?.let { setImageResource(it) }
            return
        }

        loadBlurImage(
            url = url,
            placeholder = placeholderResId,
            error = errorResId
        )
    }

    @JvmStatic
    @BindingAdapter("imageRes")
    fun ImageView.imageRes(res: Int) {
        setImageResource(res)
    }
}