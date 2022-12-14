package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.uibox.R
import com.example.uibox.databinding.ViewAlbumItemBinding
import com.example.uibox.tools.ScaleType
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.animateClick
import com.example.uibox.tools.applyStringSource
import com.example.uibox.tools.clickWithDebounce
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.squareup.picasso.Picasso

class ItemAlbumView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    data class AlbumData(
        @StringSourceData val albumId: StringSource,
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

    fun configure(albumData: AlbumData, clickAction: (AlbumData) -> Unit) {
        with(binding) {
            albumId.applyStringSource(albumData.albumId)
            albumItemsCount.applyStringSource(albumData.albumImagesCount)
        }
        clickWithDebounce {
            binding.albumId.animateClick(ScaleType.Extra) {
                clickAction(albumData)
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
