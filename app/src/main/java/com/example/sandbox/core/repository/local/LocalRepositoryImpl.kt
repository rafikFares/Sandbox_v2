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
        return Either.Success(emptyList())
    }

    override suspend fun retrieveItems(range: IntRange): Either<SandboxException, List<ItemEntity>> {
        return Either.Success(emptyList())
    }

}
