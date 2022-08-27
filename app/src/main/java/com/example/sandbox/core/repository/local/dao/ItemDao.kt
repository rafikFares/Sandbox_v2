package com.example.sandbox.core.repository.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.sandbox.core.repository.local.entity.AlbumEntity
import com.example.sandbox.core.repository.local.entity.ItemEntity

@Dao
interface ItemDao {
    /**
     * Inserts [itemEntities] into the db if they don't exist, and ignores those that do
     * return id of element if inserted, otherwise return -1
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAll(itemEntities: List<ItemEntity>): List<Long>

    /**
     * Updates [itemEntity] in the db that match the primary key, and no-ops if they don't
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(itemEntity: ItemEntity)

    /**
     * Updates [itemEntities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateItems(itemEntities: List<ItemEntity>)

    /**
     * Inserts or updates [itemEntities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertItems(itemEntities: List<ItemEntity>) = upsert(
        items = itemEntities,
        insertMany = ::insertOrIgnoreAll,
        updateMany = ::updateItems
    )

    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun retrieveItem(itemId: Int): ItemEntity?

    @Query("SELECT * FROM items ORDER BY id ASC")
    fun retrieveAllItemsPagingSource(): PagingSource<Int, ItemEntity>

    @Query("SELECT albumId, COUNT(*) AS imagesCount FROM items GROUP BY albumId")
    fun retrieveAllAlbumsPagingSource(): PagingSource<Int, AlbumEntity>

    @Query("SELECT * FROM items WHERE albumId = :albumId")
    suspend fun retrieveAllItemsOfAlbum(albumId: Int): List<ItemEntity> // could be flow without suspend

    @Query("DELETE FROM items")
    suspend fun clearAll()
}

/**
 * Performs an upsert by first attempting to insert [items] using [insertMany] with the the result
 * of the inserts returned.
 *
 * Items that were not inserted due to conflicts are then updated using [updateMany]
 */
suspend fun <T> upsert(
    items: List<T>,
    insertMany: suspend (List<T>) -> List<Long>,
    updateMany: suspend (List<T>) -> Unit
) {
    val insertResults = insertMany(items)

    // remove inserted items to update only existing/already inserted items
    val updateList = items.zip(insertResults)
        .mapNotNull { (item, insertResult) ->
            if (insertResult == -1L) item else null
        }
    if (updateList.isNotEmpty()) updateMany(updateList)
}
