package com.example.uibox.tools

import android.widget.ImageView
import com.squareup.picasso.Picasso

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD
)
annotation class ImageSourceData

sealed interface ImageSource {
    data class Drawable(@DrawableSourceData val drawableSource: DrawableSource) : ImageSource
    data class Url(val url: String) : ImageSource
}

fun ImageView.applyImageSource(imageSource: ImageSource) {
    when (imageSource) {
        is ImageSource.Drawable -> {
            applyDrawableSource(imageSource.drawableSource)
        }
        is ImageSource.Url -> {
            Picasso.get().load(imageSource.url).into(this)
        }
    }
}
