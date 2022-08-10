package com.example.sandbox.core.repository.data

import com.example.sandbox.core.utils.empty

data class ImageItem(
    val albumId: Int = 0,
    val id: Int = 0,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
) {
    companion object {
        val Empty = ImageItem(
            albumId = 0,
            id = 0,
            thumbnailUrl = String.empty(),
            title = String.empty(),
            url = String.empty()
        )
    }
}
