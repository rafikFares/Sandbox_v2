package com.example.sandbox.main.album.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.main.album.adapter.AlbumAdapter
import com.example.sandbox.main.image.adapter.ImageAdapter
import com.example.uibox.tools.SpaceItemDecoration

@BindingAdapter("bind:initAlbumAdapter", "bind:isPortraitMode")
fun RecyclerView.initAlbumAdapter(albumAdapter: AlbumAdapter, isPortraitMode: Boolean = true) {
    val spanCount = if (isPortraitMode) 3 else 6
    layoutManager = GridLayoutManager(context, spanCount)
    addItemDecoration(
        SpaceItemDecoration(
        topDP = 6,
        leftDP = 6,
        rightDP = 6,
        bottomDP = 6,
    ))
    adapter = albumAdapter
}
