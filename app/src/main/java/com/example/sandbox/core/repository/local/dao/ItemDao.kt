package com.example.sandbox.core.repository.local.dao

import com.example.sandbox.core.repository.local.entity.ItemEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.RealmChange
import kotlinx.coroutines.flow.Flow

interface ItemDao {
    fun observeRealmUpdates(): Flow<RealmChange<Realm>>
    suspend fun insertItem(itemEntity: ItemEntity)
    suspend fun insertItems(itemEntities: List<ItemEntity>)
    suspend fun retrieveItem(itemId: Int): ItemEntity?
    suspend fun retrieveAllItemsInRange(itemIdRange: IntRange): List<ItemEntity>
    suspend fun retrieveAllAlbumsInRange(albumIdRange: IntRange): List<ItemEntity>
    suspend fun retrieveAllItemsOfAlbum(albumId: Int): List<ItemEntity>
    suspend fun retrieveAllItems(): List<ItemEntity>
}
