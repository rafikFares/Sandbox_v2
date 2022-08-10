package com.example.sandbox.core.usecase

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.interactor.UseCase
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.repository.remote.model.ApiItem
import com.example.sandbox.core.utils.Either
import com.example.sandbox.core.utils.ifIsSuccessThan
import org.koin.core.annotation.Single

@Single
class FetchAndStoreItemsUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : UseCase<List<ApiItem>, String>() { // String : take a String as a params

    override suspend fun run(params: String?): Either<SandboxException, List<ApiItem>> {
        val result = remoteRepository.retrieveItems(params)

        result.ifIsSuccessThan { apiItems ->
            apiItems.map {
                it.toItemEntity()
            }.also {
                println(">>>>>>>>>>>>>>>>>>> size = ${it.size}")
                localRepository.insertItems(it)
            }
        }
        return result
    }
}
