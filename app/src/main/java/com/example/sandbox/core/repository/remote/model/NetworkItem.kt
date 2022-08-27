package com.example.sandbox.core.repository.remote.model

import com.example.sandbox.core.repository.local.entity.ItemEntity
import kotlinx.serialization.Serializable

/**
 * Network representation of [ImageItem]
 */
@Serializable
data class NetworkItem(
    val albumId: Int = 0,
    val id: Int = 0,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
)

/**
 * Convert Network results to database objects
 */
fun NetworkItem.toItemEntity(): ItemEntity {
    return ItemEntity(
        albumId,
        id,
        thumbnailUrl,
        title,
        url
    )
}
