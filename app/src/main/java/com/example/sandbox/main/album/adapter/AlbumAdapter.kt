package com.example.sandbox.main.album.adapter

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.core.data.AlbumItem
import com.example.sandbox.main.album.AlbumViewModel
import com.example.uibox.tools.StringSource
import com.example.uibox.view.ItemAlbumView

class AlbumAdapter(private val albumViewModel: AlbumViewModel) :
    PagingDataAdapter<AlbumItem, AlbumAdapter.AlbumItemViewHolder>(ALBUM_ITEM_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder =
        AlbumItemViewHolder(ItemAlbumView(parent.context))

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

    inner class AlbumItemViewHolder(
        private val view: ItemAlbumView
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        fun bind(item: AlbumItem) {
            view.configure(item.toAlbumData()) {
                albumViewModel.onAlbumClick(it.albumId)
            }
        }

        private fun AlbumItem.toAlbumData(): ItemAlbumView.AlbumData = ItemAlbumView.AlbumData(
            albumId = albumId,
            albumImagesCount = StringSource.String("$imagesCount")
        )
    }
}
