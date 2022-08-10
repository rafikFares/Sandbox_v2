package com.example.uibox.tools

import android.content.Context
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.text.color

annotation class StringSourceData

sealed interface StringSource {
    data class String(val value: kotlin.String) : StringSource

    data class Res(@StringRes val value: Int, val formatArgs: List<Any> = emptyList()) : StringSource

    data class ResPlurals(@PluralsRes val value: Int, val quantity: Int, val formatArgs: List<Any> = emptyList()) :
        StringSource

    data class Concat(val values: List<StringSource>) : StringSource

    data class SpannableString(val value: SpannableStringBuilder) : StringSource

    data class SpannableStringRes(val values: List<SpannableResData> = emptyList()) : StringSource {
        data class SpannableResData(
            val spanStringSource: StringSource,
            @ColorInt val spanColor: Int? = null
        )
    }
}

fun StringSource.toString(context: Context): String {
    return when (this) {
        is StringSource.String -> value
        is StringSource.Res -> {
            val args = formatArgs.map { arg ->
                when (arg) {
                    is StringSource -> arg.toString(context)
                    else -> arg
                }
            }.toTypedArray()
            context.getString(value, *args)
        }
        is StringSource.ResPlurals -> {
            val arrayArgs = formatArgs.map { it }.toTypedArray()
            context.resources.getQuantityString(value, quantity, *arrayArgs)
        }
        is StringSource.Concat ->
            values.joinToString("") {
                it.toString(context)
            }
        is StringSource.SpannableString -> "$value" // it is better to call applyStringSource
        is StringSource.SpannableStringRes -> { // it is better to call applyStringSource
            val spannableString = SpannableStringBuilder()
            values.forEach { (stringSource, _) -> // no color iis managed in strings only in CharSequence
                spannableString.append(stringSource.toString(context))
            }
            return "$spannableString"
        }
    }
}

fun StringSource.toCharSequence(context: Context): CharSequence {
    return when (this) {
        is StringSource.SpannableString -> value
        is StringSource.SpannableStringRes -> {
            val spannableString = SpannableStringBuilder()
            values.forEach { (stringSource, colorInt) ->
                if (colorInt != null) {
                    spannableString.color(colorInt) { append(stringSource.toCharSequence(context)) }
                } else {
                    spannableString.append(stringSource.toCharSequence(context))
                }
            }
            return spannableString
        }
        else -> toString(context)
    }
}

fun TextView.applyStringSource(stringSource: StringSource?) {
    when (stringSource) {
        is StringSource.String,
        is StringSource.Res,
        is StringSource.ResPlurals,
        is StringSource.Concat -> {
            val newText = stringSource.toString(context)
            if (text.toString() != newText) {
                text = newText
            }
        }
        is StringSource.SpannableString -> text = stringSource.value // it is colorised
        is StringSource.SpannableStringRes -> text = stringSource.toCharSequence(context) // it is colorised
        null -> {
            if (text.toString() != "") {
                text = ""
            }
        }
    }
}
