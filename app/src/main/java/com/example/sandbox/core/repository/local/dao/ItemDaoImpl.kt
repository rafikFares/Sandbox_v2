package com.example.sandbox.core.repository.local.dao

import com.example.sandbox.core.repository.local.entity.ItemEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.koin.core.annotation.Single

@Single
class ItemDaoImpl(private val realmDb: Realm) : ItemDao {

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
        return realmDb.write {
            query<ItemEntity>("id == $0", itemId)
                .first().find()
        }
    }

    override suspend fun retrieveAllItemsInRange(itemIds: List<Int>): List<ItemEntity> {
        val items = mutableListOf<ItemEntity>()
        realmDb.write {
            items.addAll(
                query<ItemEntity>("id IN $0", itemIds)
                    .find()
            )
        }
        return items
    }

    override suspend fun retrieveAllItems(): List<ItemEntity> {
        val items = mutableListOf<ItemEntity>()
        realmDb.write {
            items.addAll(
                query<ItemEntity>()
                    .find()
            )
        }
        return items
    }
}
