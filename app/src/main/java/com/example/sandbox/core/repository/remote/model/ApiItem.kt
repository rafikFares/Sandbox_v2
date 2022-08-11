package com.example.sandbox.core.repository.remote.model

import com.example.sandbox.core.repository.local.entity.ItemEntity
import kotlinx.serialization.Serializable

@Serializable
data class ApiItem(
    val albumId: Int = 0,
    val id: Int = 0,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
) {
    /**
     * Convert Network results to database objects
     */
    fun toItemEntity(): ItemEntity {
        val itemEntity = ItemEntity()
        itemEntity.albumId = albumId
        itemEntity.id = id
        itemEntity.thumbnailUrl = thumbnailUrl
        itemEntity.title = title
        itemEntity.url = url
        return itemEntity
    }
}
