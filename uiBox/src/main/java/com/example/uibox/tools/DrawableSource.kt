package com.example.uibox.tools

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD
)
annotation class DrawableSourceData

sealed interface DrawableSource {
    data class Drawable(
        val value: android.graphics.drawable.Drawable,
        @ColorSourceData val tint: ColorSource? = null
    ) : DrawableSource

    data class Res(
        @DrawableRes val drawableRes: Int,
        @ColorSourceData val tint: ColorSource? = null
    ) : DrawableSource

    fun drawable(context: Context): android.graphics.drawable.Drawable {
        return when (this) {
            is Drawable -> value
            is Res -> ContextCompat.getDrawable(context, drawableRes)!!
        }
    }
}

fun ImageView.applyDrawableSource(drawableSource: DrawableSource) {
    when (drawableSource) {
        is DrawableSource.Drawable -> {
            setImageDrawable(drawableSource.value)
            drawableSource.tint?.let {
                setColorFilter(it.color(context))
            }
        }
        is DrawableSource.Res -> {
            setImageResource(drawableSource.drawableRes)
            drawableSource.tint?.let {
                setColorFilter(it.color(context))
            }
        }
    }
}

