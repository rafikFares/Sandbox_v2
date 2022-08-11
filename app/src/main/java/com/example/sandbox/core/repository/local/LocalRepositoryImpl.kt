package com.example.sandbox.core.repository.local

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.utils.Either
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class LocalRepositoryImpl(
    private val itemDao: ItemDao,
    @Named("Dispatchers.IO") private val ioDispatcher: CoroutineContext
) : LocalRepository {

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

    override suspend fun retrieveItems(from: Int, to: Int): Either<SandboxException, List<ItemEntity>> {
        return retrieveItems(IntRange(from, to))
    }

    /**
     * used for pagination, returns all items in range id
     */
    override suspend fun retrieveItems(range: IntRange): Either<SandboxException, List<ItemEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val itemEntityTmp = itemDao.retrieveAllItemsInRange(range)
                Either.Success(itemEntityTmp)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    /**
     * used for pagination, returns all albums in range id
     */
    override suspend fun retrieveAlbums(range: IntRange): Either<SandboxException, Map<Int, Int>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val itemEntityTmp = itemDao.retrieveAllAlbumsInRange(range)
                    .groupingBy { it.albumId }
                    .eachCount()
                Either.Success(itemEntityTmp)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    /**
     * return all items of an album
     */
    override suspend fun retrieveItemsOfAlbum(albumId: Int): Either<SandboxException, List<ItemEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val itemEntityTmp = itemDao.retrieveAllItemsOfAlbum(albumId)
                Either.Success(itemEntityTmp)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    /**
     * return a map of albumIds and how much items are in each album
     */
    override suspend fun retrieveAlbums(): Either<SandboxException, Map<Int, Int>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val itemEntityTmp = itemDao.retrieveAllItems()
                    .groupingBy { it.albumId }
                    .eachCount()
                Either.Success(itemEntityTmp)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }
}
