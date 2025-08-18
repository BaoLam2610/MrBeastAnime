package com.lambao.presentation.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import java.io.File

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL
) {
    Glide.with(this)
        .load(url)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(diskCacheStrategy)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadImage(
    @DrawableRes resourceId: Int,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(resourceId)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadImage(
    uri: Uri?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(uri)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.ALL)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadImage(
    bitmap: Bitmap?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(bitmap)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadImage(
    file: File?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(file)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.DATA)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadImage(
    bytes: ByteArray?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(bytes)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadDrawable(
    drawable: Drawable?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(drawable)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadCircleImage(
    url: String?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions().circleCrop())
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.ALL)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadCircleImage(
    @DrawableRes resourceId: Int,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(resourceId)
        .apply(RequestOptions().circleCrop())
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadCircleImage(
    uri: Uri?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions().circleCrop())
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.ALL)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadCircleImage(
    bitmap: Bitmap?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(bitmap)
        .apply(RequestOptions().circleCrop())
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadCircleImage(
    file: File?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(file)
        .apply(RequestOptions().circleCrop())
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.DATA)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadCircleImage(
    bytes: ByteArray?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide.with(this)
        .load(bytes)
        .apply(RequestOptions().circleCrop())
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadRoundedImage(
    url: String?,
    cornerRadiusDp: Int,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    val cornerRadiusPx = cornerRadiusDp.toDp
    Glide.with(this)
        .load(url)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusPx)))
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.ALL)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadRoundedImage(
    @DrawableRes resourceId: Int,
    cornerRadiusDp: Int,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    val cornerRadiusPx = cornerRadiusDp.toDp
    Glide.with(this)
        .load(resourceId)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusPx)))
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadRoundedImage(
    uri: Uri?,
    cornerRadiusDp: Int,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    val cornerRadiusPx = cornerRadiusDp.toDp
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusPx)))
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.ALL)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadRoundedImage(
    bitmap: Bitmap?,
    cornerRadiusDp: Int,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    val cornerRadiusPx = cornerRadiusDp.toDp
    Glide.with(this)
        .load(bitmap)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusPx)))
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadRoundedImage(
    file: File?,
    cornerRadiusDp: Int,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    val cornerRadiusPx = cornerRadiusDp.toDp
    Glide.with(this)
        .load(file)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusPx)))
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.DATA)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadRoundedImage(
    bytes: ByteArray?,
    cornerRadiusDp: Int,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    val cornerRadiusPx = cornerRadiusDp.toDp
    Glide.with(this)
        .load(bytes)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusPx)))
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun Context.preloadImage(url: String?) {
    Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).preload()
}

fun Context.loadBitmap(
    url: String?,
    onBitmapLoaded: (Bitmap) -> Unit,
    onError: (Exception?) -> Unit = {}
) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                onBitmapLoaded(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                onError(GlideException("Bitmap load failed for URL: $url"))
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

fun ImageView.loadImageWithListener(
    url: String?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null,
    onSuccess: () -> Unit,
    onError: (Exception?) -> Unit = {}
) {
    Glide.with(this)
        .load(url)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
            diskCacheStrategy(DiskCacheStrategy.ALL)
        }
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                onError(e)
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                onSuccess()
                return false
            }
        })
        .into(this)
}

fun <T> RequestBuilder<T>.skipMemoryCache(): RequestBuilder<T> =
    this.apply(RequestOptions().skipMemoryCache(true))

fun <T> RequestBuilder<T>.withSignature(signature: String): RequestBuilder<T> =
    this.apply(RequestOptions().signature(ObjectKey(signature)))

fun Context.preloadImages(urls: List<String>) {
    urls.forEach { url ->
        if (!url.isNullOrEmpty()) {
            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .preload()
        }
    }
}

fun ImageView.clearGlide() {
    Glide.with(this).clear(this)
}


