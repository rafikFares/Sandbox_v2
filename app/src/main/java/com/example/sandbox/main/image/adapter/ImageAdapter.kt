package com.example.sandbox.main.image.adapter

import android.content.res.Configuration
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.main.image.ImageViewModel
import com.example.uibox.tools.StringSource
import com.example.uibox.view.ItemImageView

class ImageAdapter(private val imageViewModel: ImageViewModel) :
    PagingDataAdapter<ImageItem, ImageAdapter.ImageItemViewHolder>(IMAGE_ITEM_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder(ItemImageView(parent.context))

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    companion object {
        private val IMAGE_ITEM_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem == newItem
        }
    }

    inner class ImageItemViewHolder(
        private val view: ItemImageView
    ) : RecyclerView.ViewHolder(view) {

        init {
            val orientation = view.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                view.layoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            } else {
                view.layoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }

        fun bind(item: ImageItem) {
            view.configure(item.toImageData()) {
                imageViewModel.onImageItemClick(it.imageUrl)
            }
        }

        private fun ImageItem.toImageData(): ItemImageView.ImageData = ItemImageView.ImageData(
            imageAlbumId = StringSource.String("$albumId"),
            imageId = StringSource.String("$id"),
            imageTitle = StringSource.String(title),
            imageThumbnailUrl = thumbnailUrl,
            imageUrl = url
        )
    }
}
