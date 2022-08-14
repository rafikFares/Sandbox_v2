package com.example.sandbox.core.repository.local.entity

import com.example.sandbox.core.data.ImageItem
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * Defines an ImageItem DB entity.
 */
class ItemEntity : RealmObject {
    var albumId: Int = -1

    @PrimaryKey
    var id: Int = -1
    var thumbnailUrl: String = ""
    var title: String = ""
    var url: String = ""
}

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
