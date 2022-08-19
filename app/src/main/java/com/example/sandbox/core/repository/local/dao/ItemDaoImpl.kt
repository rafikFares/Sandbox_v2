package com.example.sandbox.core.repository.local.dao

import com.example.sandbox.core.repository.local.entity.ItemEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.RealmChange
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class ItemDaoImpl(private val realmDb: Realm) : ItemDao {

    override fun observeRealmUpdates(): Flow<RealmChange<Realm>> = realmDb.asFlow()

    override suspend fun insertItem(itemEntity: ItemEntity) {
        realmDb.write {
            copyToRealm(itemEntity, UpdatePolicy.ALL)
        }
    }

    override suspend fun insertItems(itemEntities: List<ItemEntity>) {
        realmDb.write {
            itemEntities.forEach {
                copyToRealm(it, UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun retrieveItem(itemId: Int): ItemEntity? {
        return realmDb.query<ItemEntity>("id == $0", itemId)
            .first().find()
    }

    override suspend fun retrieveAllItemsInRange(itemIdRange: IntRange): List<ItemEntity> {
        val items = mutableListOf<ItemEntity>()
        items.addAll(
            realmDb.query<ItemEntity>(
                "id >= $0 AND id < $1",
                itemIdRange.first,
                itemIdRange.last
            )
                .find()
        )
        return items
    }

    override suspend fun retrieveAllAlbumsInRange(albumIdRange: IntRange): List<ItemEntity> {
        val items = mutableListOf<ItemEntity>()
        items.addAll(
            realmDb.query<ItemEntity>(
                "albumId >= $0 AND albumId < $1",
                albumIdRange.first,
                albumIdRange.last
            )
                .find()
        )
        return items
    }

    override suspend fun retrieveAllItemsOfAlbum(albumId: Int): List<ItemEntity> {
        val items = mutableListOf<ItemEntity>()
        items.addAll(
            realmDb.query<ItemEntity>("albumId == $0", albumId)
                .find()
        )
        return items
    }

    override suspend fun retrieveAllItems(): List<ItemEntity> {
        val items = mutableListOf<ItemEntity>()
        items.addAll(
            realmDb.query<ItemEntity>()
                .find()
        )
        return items
    }
}
