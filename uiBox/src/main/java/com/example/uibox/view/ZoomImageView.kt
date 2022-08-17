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
import com.example.uibox.tools.ImageSource
import com.example.uibox.tools.ImageSourceData
import com.example.uibox.tools.ScaleType
import com.example.uibox.tools.animateClick
import com.example.uibox.tools.applyImageSource

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

    fun setImage(@ImageSourceData imageSource: ImageSource) {
        binding.alertImage.applyImageSource(imageSource)
    }
}

@BindingAdapter("bind:initFullImage")
fun ZoomImageView.initFullImage(url: String?) {
    url?.let {
        setImage(ImageSource.Url(it))
    }
}
