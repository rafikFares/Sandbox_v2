package com.example.sandbox.core.repository.local.entity

import com.example.sandbox.core.data.AlbumItem

/**
 * Defines an AlbumEntity DB entity.
 */
data class AlbumEntity(
    val albumId: Int,
    val imagesCount: Int
)

/**
 * Convert DB results to external objects
 */
fun AlbumEntity.toAlbumItem(): AlbumItem = AlbumItem(
    albumId = albumId,
    imagesCount = imagesCount
)
