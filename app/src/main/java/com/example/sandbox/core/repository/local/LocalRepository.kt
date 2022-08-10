package com.example.sandbox.core.repository.local

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.utils.Either

interface LocalRepository {
    suspend fun insertItems(items: List<ItemEntity>): Either<SandboxException, Boolean>
    suspend fun retrieveItems(from: Int = 0, to: Int): Either<SandboxException, List<ItemEntity>>
    suspend fun retrieveItems(range: IntRange): Either<SandboxException, List<ItemEntity>>
}
