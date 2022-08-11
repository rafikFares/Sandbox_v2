package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.uibox.R
import com.example.uibox.databinding.ViewAlbumItemBinding
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.animateClick
import com.example.uibox.tools.applyStringSource
import com.example.uibox.tools.clickWithDebounce
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel

class ItemAlbumView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    data class AlbumData(
        val albumId: Int,
        @StringSourceData val albumImagesCount: StringSource
    )

    private val binding = ViewAlbumItemBinding.inflate(LayoutInflater.from(context), this@ItemAlbumView, true)

    init {
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_text_inverted))
        cardElevation = resources.getDimension(R.dimen.elevation_12)
        preventCornerOverlap = true
        clipToPadding = true

        shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, resources.getDimension(R.dimen.radius_25))
            .build()
    }

    fun configure(imageData: AlbumData, clickAction: (AlbumData) -> Unit) {
        with(binding) {
            albumId.text = "$albumId"
            albumItemsCount.applyStringSource(imageData.albumImagesCount)
        }
        clickWithDebounce {
            binding.albumIcon.animateClick {
                clickAction(imageData)
            }
        }
    }
}

@BindingAdapter("bind:init", "bind:onClick")
fun ItemAlbumView.init(albumData: ItemAlbumView.AlbumData?, clickAction: (ItemAlbumView.AlbumData) -> Unit) {
    albumData?.let {
        configure(it, clickAction)
    }
}
