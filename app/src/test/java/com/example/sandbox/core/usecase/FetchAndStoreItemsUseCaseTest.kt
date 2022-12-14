package com.example.sandbox.core.usecase

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.sandbox.core.repository.remote.NetworkService
import com.example.sandbox.core.utils.Either
import com.example.sandbox.core.utils.currentTime
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf

@OptIn(ExperimentalCoroutinesApi::class)
class FetchAndStoreItemsUseCaseTest : BaseUnitTest() {
    private lateinit var fetchAndStoreItemsUseCase: FetchAndStoreItemsUseCase

    @MockK
    private lateinit var clock: Clock.System

    @MockK
    private lateinit var networkService: NetworkService

    @MockK
    private lateinit var localRepository: LocalRepository

    @MockK
    private lateinit var preferenceRepository: PreferenceRepository

    @BeforeTest
    fun setUp() {
        fetchAndStoreItemsUseCase = FetchAndStoreItemsUseCase(
            networkService,
            localRepository,
            preferenceRepository
        )
    }

    @Test
    fun noNeedToUpdateCache() = runTest {
        coEvery {
            preferenceRepository.get(PreferenceKey.LastFetch, any())
        } returns currentTime

        val result = fetchAndStoreItemsUseCase.run("")
        result shouldBeInstanceOf Either.Success::class.java
        (result as Either.Success).value shouldBe false
    }

    @Test
    fun fetchUpdateCache() = runTest {
        val currentTimeTest = currentTime // real time
        every {
            clock.now().epochSeconds
        } returns currentTimeTest

        val lastTime = currentTimeTest - 60 * 61 // 1 hour and 1 minute

        coEvery {
            preferenceRepository.get(PreferenceKey.LastFetch, any())
        } returns lastTime
        coEvery {
            networkService.retrieveItems(any())
        } returns Either.Success(emptyList())
        coEvery {
            localRepository.insertItems(any())
        } returns Either.Success(true)

        val result = fetchAndStoreItemsUseCase.run("")
        coVerify(exactly = 1) {
            preferenceRepository.save(PreferenceKey.LastFetch, currentTimeTest)
        }
    }
}
