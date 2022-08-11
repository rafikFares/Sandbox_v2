package com.example.sandbox.core.repository.local

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.utils.Either
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    enum class DataState { Initialized, Updated }

    fun observeDataState(): Flow<DataState>
    suspend fun insertItems(items: List<ItemEntity>): Either<SandboxException, Boolean>
    suspend fun retrieveItems(from: Int = 0, to: Int): Either<SandboxException, List<ItemEntity>>
    suspend fun retrieveItems(range: IntRange): Either<SandboxException, List<ItemEntity>>
    suspend fun retrieveAlbums(range: IntRange): Either<SandboxException, Map<Int, Int>>
    suspend fun retrieveItemsOfAlbum(albumId: Int): Either<SandboxException, List<ItemEntity>>
    suspend fun retrieveAlbums(): Either<SandboxException, Map<Int, Int>>
}
