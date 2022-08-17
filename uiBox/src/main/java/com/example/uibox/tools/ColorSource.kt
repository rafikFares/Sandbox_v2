package com.example.uibox.tools

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
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
annotation class ColorSourceData

sealed interface ColorSource {
    data class Res(@ColorRes val value: kotlin.Int) : ColorSource
    data class Int(@ColorInt val value: kotlin.Int) : ColorSource

    @ColorInt
    fun color(context: Context): kotlin.Int {
        return when (this) {
            is Res -> {
                ContextCompat.getColor(context, value)
            }
            is Int -> value
        }
    }
}
