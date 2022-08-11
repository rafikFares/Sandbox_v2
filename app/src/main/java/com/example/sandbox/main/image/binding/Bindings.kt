package com.example.sandbox.main.image.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.main.image.adapter.ImageAdapter
import com.example.uibox.tools.SpaceItemDecoration

@BindingAdapter("bind:initAdapter")
fun RecyclerView.initAdapter(imageAdapter: ImageAdapter) {
    addItemDecoration(
        SpaceItemDecoration(
        topDP = 12,
        leftDP = 12,
        rightDP = 12,
        bottomDP = 12,
    ))
    adapter = imageAdapter
}
