package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.uibox.R
import com.example.uibox.databinding.ViewImageItemBinding
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.animateClick
import com.example.uibox.tools.applyStringSource
import com.example.uibox.tools.clickWithDebounce
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.squareup.picasso.Picasso


class ItemImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    data class ImageData(
        @StringSourceData val imageAlbumId: StringSource,
        @StringSourceData val imageId: StringSource,
        @StringSourceData val imageTitle: StringSource,
        val imageThumbnailUrl: String,
        val imageUrl: String
    )

    private val binding = ViewImageItemBinding.inflate(LayoutInflater.from(context), this@ItemImageView, true)

    init {
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_text_inverted))
        cardElevation = resources.getDimension(R.dimen.elevation_12)
        preventCornerOverlap = true
        clipToPadding = true

        shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, resources.getDimension(R.dimen.radius_25))
            .build()
    }

    fun configure(imageData: ImageData, clickAction: (ImageData) -> Unit) {
        with(binding) {
            imageId.applyStringSource(imageData.imageId)
            imageTitle.applyStringSource(imageData.imageTitle)
            Picasso.get().load(imageData.imageThumbnailUrl).into(imageIcon)
        }
        clickWithDebounce {
            binding.imageIcon.animateClick {
                clickAction(imageData)
            }
        }
    }
}

@BindingAdapter("bind:init", "bind:onClick")
fun ItemImageView.init(imageData: ItemImageView.ImageData?, clickAction: (ItemImageView.ImageData) -> Unit) {
    imageData?.let {
        configure(it, clickAction)
    }
}
