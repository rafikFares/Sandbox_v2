package com.example.sandbox.main.album.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.sandbox.core.repository.data.AlbumItem

class AlbumAdapter : PagingDataAdapter<AlbumItem, AlbumItemViewHolder>(ALBUM_ITEM_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder =
        AlbumItemViewHolder.fromParent(parent)

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    companion object {
        private val ALBUM_ITEM_DIFF_CALLBACK = object : DiffUtil.ItemCallback<AlbumItem>() {
            override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean =
                oldItem.albumId == newItem.albumId

            override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean =
                oldItem == newItem
        }
    }
}
