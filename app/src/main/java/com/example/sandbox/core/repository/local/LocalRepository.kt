package com.example.sandbox.core.repository.local

import com.example.sandbox.core.data.AlbumItem
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.utils.Either
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    enum class DataState { Initialized, Updated }

    fun observeDataState(): Flow<DataState>
    suspend fun insertItems(items: List<ItemEntity>): Either<SandboxException, Boolean>
    suspend fun retrieveItems(from: Int = 0, to: Int): Either<SandboxException, List<ImageItem>>
    suspend fun retrieveItems(range: IntRange): Either<SandboxException, List<ImageItem>>
    suspend fun retrieveAlbums(range: IntRange): Either<SandboxException, List<AlbumItem>>
    suspend fun retrieveItemsOfAlbum(albumId: Int): Either<SandboxException, List<ImageItem>>
    suspend fun retrieveAlbums(): Either<SandboxException, List<AlbumItem>>
}
