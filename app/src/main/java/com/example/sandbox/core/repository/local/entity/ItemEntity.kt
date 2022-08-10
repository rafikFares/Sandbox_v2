package com.example.sandbox.core.repository.local.entity

import com.example.sandbox.core.repository.data.ImageItem
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ItemEntity : RealmObject {
    var albumId: Int = -1

    @PrimaryKey
    var id: Int = -1
    var thumbnailUrl: String = ""
    var title: String = ""
    var url: String = ""

    fun toImageItem(): ImageItem = ImageItem(
        albumId = albumId,
        id = id,
        thumbnailUrl = thumbnailUrl,
        title = title,
        url = url
    )
}
