package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.example.uibox.R
import com.example.uibox.databinding.ViewAlertInformationBinding
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.applyStringSource
import com.google.android.material.card.MaterialCardView

class InformationAlertView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    data class Information(
        @StringSourceData val text: StringSource,
        @DrawableRes val icon: Int = R.drawable.ic_baseline_notifications,
        @ColorRes val backgroundColor: Int = R.color.color_red
    )

    private val binding = ViewAlertInformationBinding.inflate(LayoutInflater.from(context), this@InformationAlertView, true)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        radius = resources.getDimension(R.dimen.spacing_xl)
        cardElevation = resources.getDimension(R.dimen.elevation_6)
        clipToPadding = true
        isClickable = true
        isFocusable = true
    }

    fun configure(information: Information): InformationAlertView {
        setCardBackgroundColor(ContextCompat.getColor(context, information.backgroundColor))
        binding.informationIcon.setImageResource(information.icon)
        binding.informationText.applyStringSource(information.text)
        return this
    }

    companion object {
        operator fun invoke(context: Context, information: Information): InformationAlertView =
            InformationAlertView(context).configure(information)
    }
}
