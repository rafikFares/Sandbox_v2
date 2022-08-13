package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.uibox.R
import com.example.uibox.databinding.ViewImageZoomBinding
import com.example.uibox.tools.ScaleType
import com.example.uibox.tools.animateClick
import com.squareup.picasso.Picasso

class ZoomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewImageZoomBinding.inflate(LayoutInflater.from(context), this)

    init {
        val background = ContextCompat.getColor(context, R.color.color_overlay_background)
        setBackgroundColor(background)
        setOnClickListener {
            binding.back.performClick()
        }
        binding.back.setOnClickListener {
            it.animateClick(ScaleType.Big) {
                isVisible = false
            }
        }
    }

    var image: String = ""
        set(value) {
            field = value
            Picasso.get().load(value).into(binding.alertImage)
        }
}

@BindingAdapter("bind:initFullImage")
fun ZoomImageView.initFullImage(url: String?) {
    url?.let {
        image = it
    }
}
