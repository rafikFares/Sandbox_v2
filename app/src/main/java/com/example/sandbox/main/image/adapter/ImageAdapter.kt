package com.example.sandbox.main.image.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.sandbox.core.repository.data.ImageItem

class ImageAdapter : PagingDataAdapter<ImageItem, ImageItemViewHolder>(IMAGE_ITEM_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder.fromParent(parent)

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
}
