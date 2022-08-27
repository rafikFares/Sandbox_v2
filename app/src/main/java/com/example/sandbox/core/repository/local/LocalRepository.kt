package com.example.sandbox.core.repository.local

import androidx.paging.PagingData
import com.example.sandbox.core.data.AlbumItem
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.utils.Either
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertOrIgnoreAll(itemEntities: List<ItemEntity>): Either<SandboxException, Boolean>
    suspend fun retrieveItemsOfAlbum(albumId: Int): Either<SandboxException, List<ImageItem>>
    fun getPagingImageItemFlow(): Flow<PagingData<ImageItem>>
    fun getPagingAlbumItemFlow(): Flow<PagingData<AlbumItem>>
    suspend fun updateItem(itemEntity: ItemEntity): Either<SandboxException, Boolean>
    suspend fun updateItems(itemEntities: List<ItemEntity>): Either<SandboxException, Boolean>
    suspend fun upsertItems(itemEntities: List<ItemEntity>): Either<SandboxException, Boolean>
    suspend fun retrieveItem(itemId: Int): Either<SandboxException, ImageItem>
    suspend fun clearAll()
}
