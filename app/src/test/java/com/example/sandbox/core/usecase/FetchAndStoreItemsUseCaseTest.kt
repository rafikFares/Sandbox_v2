package com.example.sandbox.core.usecase

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf


@OptIn(ExperimentalCoroutinesApi::class)
class FetchAndStoreItemsUseCaseTest : BaseUnitTest() {
    private lateinit var fetchAndStoreItemsUseCase: FetchAndStoreItemsUseCase

    @MockK
    private lateinit var remoteRepository: RemoteRepository

    @MockK
    private lateinit var localRepository: LocalRepository

    @MockK
    private lateinit var preferenceRepository: PreferenceRepository

    @BeforeTest
    fun setUp() {
        fetchAndStoreItemsUseCase = FetchAndStoreItemsUseCase(remoteRepository, localRepository, preferenceRepository)
    }

    @Test
    fun noNeedToUpdateCache() = runTest {
        val currentTime = System.currentTimeMillis()
        coEvery {
            preferenceRepository.get(PreferenceKey.LastFetch, any())
        } returns currentTime

        val result = fetchAndStoreItemsUseCase.run("")
        result shouldBeInstanceOf Either.Success::class.java
        (result as Either.Success).value shouldBe false
    }

    @Test
    fun fetchUpdateCache() = runTest {
        Instant.now(
            Clock.fixed(
                Instant.parse("2022-08-11T12:34:01Z"), ZoneOffset.UTC
            )
        )
        val currentTime = Instant.now().epochSecond
        val lastTime = currentTime - 60 * 61 // 1 hour and 1 minute

        coEvery {
            preferenceRepository.get(PreferenceKey.LastFetch, any())
        } returns lastTime
        coEvery {
            remoteRepository.retrieveItems(any())
        } returns Either.Success(emptyList())
        coEvery {
            localRepository.insertItems(any())
        } returns Either.Success(true)

        val result = fetchAndStoreItemsUseCase.run("")
        coVerify {
            preferenceRepository.save(PreferenceKey.LastFetch, currentTime)
        }
    }
}
