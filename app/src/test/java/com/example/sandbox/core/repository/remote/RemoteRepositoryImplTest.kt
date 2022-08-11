package com.example.sandbox.core.repository.remote

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.api.ServiceApi
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.platform.NetworkHandler
import com.example.sandbox.core.utils.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf

@ExperimentalCoroutinesApi
class RemoteRepositoryImplTest : BaseUnitTest() {
    private val unconfinedDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var serviceApi: ServiceApi

    private lateinit var remoteDataRepository: RemoteRepository

    @BeforeTest
    fun before() {
        remoteDataRepository = RemoteRepositoryImpl(serviceApi, networkHandler, unconfinedDispatcher)
    }

    @Test
    fun remoteRepositoryShouldReturnNetworkException() = runTest {
        every { networkHandler.isNetworkAvailable() } returns false
        val result = remoteDataRepository.retrieveItems("")
        result shouldBeInstanceOf Either.Failure::class.java
        (result as Either.Failure).value shouldBe SandboxException.NetworkConnectionException
    }

    @Test
    fun remoteRepositoryShouldReturnEmptyParamsException() = runTest {
        every { networkHandler.isNetworkAvailable() } returns true
        val result = remoteDataRepository.retrieveItems("")
        result shouldBeInstanceOf Either.Failure::class.java
        (result as Either.Failure).value shouldBe SandboxException.EmptyParamsException
    }
}
