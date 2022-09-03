package com.example.sandbox.main.album

import app.cash.turbine.test
import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.MainDispatcherRule
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumViewModelTest : BaseAndroidTest() {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var localRepository: LocalRepository
    private lateinit var albumViewModel: AlbumViewModel

    @BeforeTest
    fun setUp() {
        every {
            localRepository.getPagingAlbumItemFlow()
        } returns emptyFlow()
        albumViewModel = AlbumViewModel(localRepository)
    }

    @Test
    fun initUiStateIsInit() = runTest(mainDispatcherRule.testDispatcher) {
        albumViewModel.uiState.test {
            expectMostRecentItem() shouldBeEqualTo AlbumViewModel.UiState.Init
            cancel()
        }
    }

    @Test
    fun onAlbumClickUpdateUiStateWithAlbumClick() = runTest(mainDispatcherRule.testDispatcher) {
        coEvery {
            localRepository.retrieveItemsOfAlbum(any())
        } returns Either.Success(emptyList())

        albumViewModel.uiState.test {
            skipItems(1)
            albumViewModel.onAlbumClick(0)
            awaitItem() shouldBeEqualTo AlbumViewModel.UiState.AlbumClick(emptyList())
            cancel()
        }
    }
}
