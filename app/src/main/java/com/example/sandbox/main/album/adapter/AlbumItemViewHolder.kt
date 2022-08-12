package com.example.sandbox.main.album.adapter

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.core.repository.data.AlbumItem
import com.example.uibox.tools.StringSource
import com.example.uibox.view.ItemAlbumView

class AlbumItemViewHolder(
    private val view: ItemAlbumView
) : RecyclerView.ViewHolder(view) {

    fun bind(item: AlbumItem) {
        view.configure(item.toAlbumData()) {
            // to do click
        }
    }

    private fun AlbumItem.toAlbumData(): ItemAlbumView.AlbumData = ItemAlbumView.AlbumData(
        albumId = albumId,
        albumImagesCount = StringSource.String("$imagesCount")
    )

    companion object {
        fun fromParent(parent: ViewGroup): AlbumItemViewHolder = ItemAlbumView(parent.context).apply {
            layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }.let {
            return AlbumItemViewHolder(it)
        }
    }
}
