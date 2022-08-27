package com.example.sandbox.core.repository.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.sandbox.core.data.AlbumItem
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.entity.AlbumEntity
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.repository.local.entity.toAlbumItem
import com.example.sandbox.core.repository.local.entity.toImageItem
import com.example.sandbox.core.utils.Either
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private const val ITEMS_PER_PAGE = 20

@Single
class LocalRepositoryImpl(
    private val itemDao: ItemDao,
    @Named("Dispatchers.IO") private val ioDispatcher: CoroutineContext
) : LocalRepository {

    override suspend fun insertOrIgnoreAll(itemEntities: List<ItemEntity>): Either<SandboxException, Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                itemDao.insertOrIgnoreAll(itemEntities)
                Either.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

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

    override fun getPagingImageItemFlow(): Flow<PagingData<ImageItem>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { itemDao.retrieveAllItemsPagingSource() }
    ).flow.map {
        it.map(ItemEntity::toImageItem)
    }

    override fun getPagingAlbumItemFlow(): Flow<PagingData<AlbumItem>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { itemDao.retrieveAllAlbumsPagingSource() }
    ).flow.map {
        it.map(AlbumEntity::toAlbumItem)
    }

    override suspend fun updateItem(itemEntity: ItemEntity): Either<SandboxException, Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                itemDao.updateItem(itemEntity)
                Either.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    override suspend fun updateItems(itemEntities: List<ItemEntity>): Either<SandboxException, Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                itemDao.updateItems(itemEntities)
                Either.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    override suspend fun upsertItems(itemEntities: List<ItemEntity>): Either<SandboxException, Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                itemDao.upsertItems(itemEntities)
                Either.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    override suspend fun retrieveItem(itemId: Int): Either<SandboxException, ImageItem> =
        withContext(ioDispatcher) {
            return@withContext try {
                val item = itemDao.retrieveItem(itemId)
                item?.let {
                    Either.Success(it.toImageItem())
                } ?: Either.Failure(SandboxException.ElementNotFoundException(itemId))
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }

    override suspend fun clearAll() =
        withContext(ioDispatcher) {
            itemDao.clearAll()
        }
}
