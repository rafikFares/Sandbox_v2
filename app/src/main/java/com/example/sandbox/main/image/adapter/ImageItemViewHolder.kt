package com.example.sandbox.main.image.adapter

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.core.repository.data.ImageItem
import com.example.uibox.tools.StringSource
import com.example.uibox.view.ItemImageView

class ImageItemViewHolder(
    private val view: ItemImageView
) : RecyclerView.ViewHolder(view) {

    fun bind(item: ImageItem) {
        view.configure(item.toImageData()) {
            // to do click
        }
    }

    private fun ImageItem.toImageData(): ItemImageView.ImageData = ItemImageView.ImageData(
        imageAlbumId = StringSource.String("$albumId"),
        imageId = StringSource.String("$id"),
        imageTitle = StringSource.String(title),
        imageThumbnailUrl = thumbnailUrl,
        imageUrl = url
    )

    companion object {
        fun fromParent(parent: ViewGroup): ImageItemViewHolder = ItemImageView(parent.context).apply {
            layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }.let {
            return ImageItemViewHolder(it)
        }
    }
}
