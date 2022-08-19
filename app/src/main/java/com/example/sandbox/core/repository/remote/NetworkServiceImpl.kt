package com.example.sandbox.core.repository.remote

import com.example.sandbox.core.api.ServiceApi
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.platform.NetworkHandler
import com.example.sandbox.core.repository.remote.model.NetworkItem
import com.example.sandbox.core.utils.Either
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class NetworkServiceImpl(
    private val serviceApi: ServiceApi,
    private val networkHandler: NetworkHandler,
    @Named("Dispatchers.IO") private val ioDispatcher: CoroutineContext
) : NetworkService {

    override suspend fun retrieveItems(params: String?): Either<SandboxException, List<NetworkItem>> =
        withContext(ioDispatcher) {
            if (networkHandler.isNetworkAvailable()) {
                try {
                    if (params.isNullOrBlank()) return@withContext Either.Failure(
                        SandboxException.EmptyParamsException
                    )

                    val response = serviceApi.fetchImages(params)
                    if (response.isSuccessful) {
                        val bodyData = response.body() ?: emptyList()
                        return@withContext Either.Success(bodyData)
                    }
                    return@withContext Either.Failure(SandboxException.ServerErrorException())
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@withContext Either.Failure(
                        SandboxException.ServerErrorException(e.message)
                    )
                }
            } else {
                return@withContext Either.Failure(SandboxException.NetworkConnectionException)
            }
        }
}
