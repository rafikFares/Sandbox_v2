package com.example.sandbox.core.repository.local.dao

import com.example.sandbox.core.repository.local.entity.ItemEntity

interface ItemDao {
    suspend fun insertItem(itemEntity: ItemEntity)
    suspend fun insertItems(itemEntities: List<ItemEntity>)
    suspend fun retrieveItem(itemId: Int): ItemEntity?
    suspend fun retrieveAllItemsInRange(itemIds: List<Int>): List<ItemEntity>
    suspend fun retrieveAllItems(): List<ItemEntity>
}
