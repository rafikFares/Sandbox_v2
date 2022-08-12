package com.example.sandbox.core.usecase

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.interactor.UseCase
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.utils.Either
import com.example.sandbox.core.utils.currentTime
import com.example.sandbox.core.utils.ifIsSuccessThan
import java.time.Instant
import org.koin.core.annotation.Single

private const val CACHE_TIMEOUT = 60 * 60 // 1 hour

@Single
class FetchAndStoreItemsUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val preferenceRepository: PreferenceRepository
) : UseCase<Boolean, String>() { // Boolean : true if fetch is done // String : take a String as a params

    override suspend fun run(params: String?): Either<SandboxException, Boolean> {
        val lastTimeFetchInMillis = preferenceRepository.get(PreferenceKey.LastFetch, 0L) as Long

        if (currentTime - lastTimeFetchInMillis > CACHE_TIMEOUT) {
            println(">>>>>>>>>>>>>>>>>>> fetching")
            preferenceRepository.save(PreferenceKey.LastFetch, currentTime)
            val result = remoteRepository.retrieveItems(params)
            result.ifIsSuccessThan { apiItems ->
                apiItems.map {
                    it.toItemEntity()
                }.also {
                    println(">>>>>>>>>>>>>>>>>>> size = ${it.size}")
                    return localRepository.insertItems(it)
                }
            }
            return result as Either.Failure
        }
        println(">>>>>>>>>>>>>>>>>>> no need to fetching")
        return Either.Success(false)
    }
}
