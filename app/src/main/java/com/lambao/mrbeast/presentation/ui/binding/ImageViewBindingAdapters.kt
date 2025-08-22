package com.lambao.mrbeast.presentation.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.lambao.presentation.extension.loadBlurImage
import com.lambao.presentation.extension.loadCircleImage
import com.lambao.presentation.extension.loadImage
import com.lambao.presentation.extension.loadRoundedImage

object ImageViewBindingAdapters {

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

        loadImage(
            url = url,
            placeholder = placeholderResId,
            error = errorResId
        )
    }

    @JvmStatic
    @BindingAdapter(
        "imageUrl",
        "placeholderResId",
        "errorResId",
        "cornerRadiusDp",
        requireAll = false
    )
    fun ImageView.loadRoundedImageUrl(
        url: String?,
        placeholderResId: Int? = null,
        errorResId: Int? = null,
        cornerRadiusDp: Int? = null
    ) {
        if (url.isNullOrBlank()) {
            placeholderResId?.let { setImageResource(it) }
            return
        }

        cornerRadiusDp?.let {
            loadRoundedImage(
                url = url,
                placeholder = placeholderResId,
                error = errorResId,
                cornerRadiusDp = it
            )
        } ?: run {
            loadImage(
                url = url,
                placeholder = placeholderResId,
                error = errorResId
            )
        }
    }

    @JvmStatic
    @BindingAdapter(
        "circleImageUrl",
        "placeholderResId",
        "errorResId",
        requireAll = false
    )
    fun ImageView.loadCircleImageUrl(
        url: String?,
        placeholderResId: Int? = null,
        errorResId: Int? = null
    ) {
        if (url.isNullOrBlank()) {
            placeholderResId?.let { setImageResource(it) }
            return
        }

        loadCircleImage(
            url = url,
            placeholder = placeholderResId,
            error = errorResId
        )
    }

    @JvmStatic
    @BindingAdapter(
        "blurImageUrl",
        "placeholderResId",
        "errorResId",
        "blurRadius",
        requireAll = false
    )
    fun ImageView.loadBlurImageUrl(
        url: String?,
        placeholderResId: Int? = null,
        errorResId: Int? = null,
        blurRadius: Int? = null,
    ) {
        if (url.isNullOrBlank()) {
            placeholderResId?.let { setImageResource(it) }
            return
        }

        loadBlurImage(
            url = url,
            placeholder = placeholderResId,
            error = errorResId,
            blurRadius = blurRadius ?: 10
        )
    }

    @JvmStatic
    @BindingAdapter("imageRes")
    fun ImageView.imageRes(res: Int) {
        setImageResource(res)
    }
}