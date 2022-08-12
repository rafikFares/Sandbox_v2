package com.example.sandbox.main.album.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.main.album.adapter.AlbumAdapter
import com.example.sandbox.main.image.adapter.ImageAdapter
import com.example.uibox.tools.SpaceItemDecoration

@BindingAdapter("bind:initAlbumAdapter")
fun RecyclerView.initAlbumAdapter(albumAdapter: AlbumAdapter) {
    layoutManager = GridLayoutManager(context, 3)
    addItemDecoration(
        SpaceItemDecoration(
        topDP = 6,
        leftDP = 6,
        rightDP = 6,
        bottomDP = 6,
    ))
    adapter = albumAdapter
}
