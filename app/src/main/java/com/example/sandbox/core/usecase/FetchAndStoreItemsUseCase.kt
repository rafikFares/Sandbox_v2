package com.example.sandbox.core.usecase

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.interactor.UseCase
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.sandbox.core.repository.remote.NetworkService
import com.example.sandbox.core.repository.remote.model.NetworkItem
import com.example.sandbox.core.repository.remote.model.toItemEntity
import com.example.sandbox.core.utils.Either
import com.example.sandbox.core.utils.currentTime
import com.example.sandbox.core.utils.ifIsSuccessThan
import org.koin.core.annotation.Single

private const val CACHE_TIMEOUT = 60 * 60 // 1 hour

@Single
class FetchAndStoreItemsUseCase(
    private val networkService: NetworkService,
    private val localRepository: LocalRepository,
    private val preferenceRepository: PreferenceRepository
) : UseCase<Boolean, String>() { // Boolean : true if fetch is done // String : take a String as a params

    override suspend fun run(params: String?): Either<SandboxException, Boolean> {
        val lastTimeFetchInMillis = preferenceRepository.get(PreferenceKey.LastFetch, 0L) as Long

        if (currentTime - lastTimeFetchInMillis > CACHE_TIMEOUT) {
            val result = networkService.retrieveItems(params)
            result.ifIsSuccessThan { networkItems ->
                networkItems.map(NetworkItem::toItemEntity)
                    .also {
                        val insertResult = localRepository.insertOrIgnoreAll(it)
                        preferenceRepository.save(PreferenceKey.LastFetch, currentTime)
                        return insertResult
                    }
            }
            return result as Either.Failure
        }
        return Either.Success(false)
    }
}
