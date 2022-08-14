package com.example.sandbox.core.repository.local

import com.example.sandbox.core.data.AlbumItem
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.repository.local.entity.toImageItem
import com.example.sandbox.core.utils.Either
import io.realm.kotlin.notifications.InitialRealm
import io.realm.kotlin.notifications.UpdatedRealm
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class LocalRepositoryImpl(
    private val itemDao: ItemDao,
    @Named("Dispatchers.IO") private val ioDispatcher: CoroutineContext
) : LocalRepository {

    /**
     * observe DB updates
     */
    override fun observeDataState(): Flow<LocalRepository.DataState> =
        itemDao.observeRealmUpdates().map { realmChange ->
            when (realmChange) {
                is InitialRealm -> LocalRepository.DataState.Initialized
                is UpdatedRealm -> LocalRepository.DataState.Updated
            }
        }

    /**
     * insert items
     */
    override suspend fun insertItems(items: List<ItemEntity>): Either<SandboxException, Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                itemDao.insertItems(items)
                Either.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    override suspend fun retrieveItems(from: Int, to: Int): Either<SandboxException, List<ImageItem>> =
        retrieveItems(IntRange(from, to))

    /**
     * used for pagination, returns all items in range id
     */
    override suspend fun retrieveItems(range: IntRange): Either<SandboxException, List<ImageItem>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val data = itemDao.retrieveAllItemsInRange(range)
                    .map(ItemEntity::toImageItem)
                Either.Success(data)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    /**
     * used for pagination, returns all albums in range id
     */
    override suspend fun retrieveAlbums(range: IntRange): Either<SandboxException, List<AlbumItem>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val itemEntityTmp = itemDao.retrieveAllAlbumsInRange(range)
                    .groupingBy { it.albumId }
                    .eachCount()
                Either.Success(itemEntityTmp.map { AlbumItem(it.key, it.value) })
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    /**
     * return all items of an album
     */
    override suspend fun retrieveItemsOfAlbum(albumId: Int): Either<SandboxException, List<ImageItem>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val itemEntityTmp = itemDao.retrieveAllItemsOfAlbum(albumId)
                Either.Success(itemEntityTmp.map(ItemEntity::toImageItem))
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    /**
     * return a map of albumIds and how much items are in each album
     */
    override suspend fun retrieveAlbums(): Either<SandboxException, List<AlbumItem>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val itemEntityTmp = itemDao.retrieveAllItems()
                    .groupingBy { it.albumId }
                    .eachCount()
                Either.Success(itemEntityTmp.map { AlbumItem(it.key, it.value) })
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }
}
