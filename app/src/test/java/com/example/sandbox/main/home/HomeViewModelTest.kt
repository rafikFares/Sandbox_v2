package com.example.sandbox.main.home

import app.cash.turbine.test
import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.MainDispatcherRule
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.sandbox.core.repository.remote.NetworkService
import com.example.sandbox.core.usecase.FetchAndStoreItemsUseCase
import com.example.sandbox.core.utils.Either
import com.example.sandbox.core.utils.currentTime
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest : BaseAndroidTest() {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fetchAndStoreItemsUseCase: FetchAndStoreItemsUseCase

    @MockK
    private lateinit var networkService: NetworkService

    @MockK
    private lateinit var localRepository: LocalRepository

    @MockK
    private lateinit var preferenceRepository: PreferenceRepository

    @BeforeTest
    fun setUp() {
        fetchAndStoreItemsUseCase = FetchAndStoreItemsUseCase(networkService, localRepository, preferenceRepository)
    }

    @Test
    fun onRefreshLoadDataAlreadyUpToDate() = runTest {
        coEvery {
            preferenceRepository.get(PreferenceKey.LastFetch, any())
        } returns currentTime

        val homeViewModel = HomeViewModel(fetchAndStoreItemsUseCase)

        homeViewModel.uiState.test {
            homeViewModel.onRefreshClick()
            awaitItem() shouldBeEqualTo HomeViewModel.UiState.Loading
            awaitItem() shouldBeEqualTo HomeViewModel.UiState.AlreadyUpToDate
        }
    }

    @Test
    fun onRefreshLoadDataUpdated() = runTest {
        coEvery {
            preferenceRepository.get(PreferenceKey.LastFetch, any())
        } returns currentTime - 60 * 120 // - 2 hours
        coEvery {
            networkService.retrieveItems(any())
        } returns Either.Success(emptyList())
        coEvery {
            localRepository.insertItems(any())
        } returns Either.Success(true)

        val homeViewModel = HomeViewModel(fetchAndStoreItemsUseCase)

        homeViewModel.uiState.test {
            homeViewModel.onRefreshClick()
            awaitItem() shouldBeEqualTo HomeViewModel.UiState.Loading
            awaitItem() shouldBeEqualTo HomeViewModel.UiState.Updated
        }
    }
}
