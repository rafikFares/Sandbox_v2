package com.example.sandbox.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.core.repository.data.ImageItem
import com.example.sandbox.databinding.ImageItemViewholderBinding

class ImageAdapter : PagingDataAdapter<ImageItem, ImageAdapter.ImageItemViewHolder>(IMAGE_ITEM_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder(
            ImageItemViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    class ImageItemViewHolder(
        private val binding: ImageItemViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageItem) {
            binding.apply {
                binding.title.text = "${item.id}"
                binding.description.text = item.title
                binding.created.text = item.url
            }
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
