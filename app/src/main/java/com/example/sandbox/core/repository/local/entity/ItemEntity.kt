package com.example.sandbox.core.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sandbox.core.data.ImageItem

/**
 * Defines an ImageItem DB entity.
 */
@Entity(tableName = "items")
data class ItemEntity(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)

/**
 * Convert DB results to external objects
 */
fun ItemEntity.toImageItem(): ImageItem = ImageItem(
    albumId = albumId,
    id = id,
    thumbnailUrl = thumbnailUrl,
    title = title,
    url = url
)
