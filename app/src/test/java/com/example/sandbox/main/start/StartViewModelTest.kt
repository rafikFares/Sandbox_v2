package com.example.sandbox.main.start

import app.cash.turbine.test
import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.MainDispatcherRule
import com.example.sandbox.core.session.UserSession
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class StartViewModelTest : BaseAndroidTest() {
    @MockK
    private lateinit var userSession: UserSession

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun userIsConnectedUpdateUiState() = runTest(UnconfinedTestDispatcher()) {
        coEvery {
            userSession.isUserLoggedIn()
        } returns true

        val startViewModel = StartViewModel(userSession)

        startViewModel.uiState.test {
            assertEquals(StartViewModel.UiState.Init, awaitItem())
            assertEquals(StartViewModel.UiState.Connected, awaitItem())
            cancel()
        }
    }

    @Test
    fun userIsNotConnectedUpdateUiState() = runTest(UnconfinedTestDispatcher()) {
        coEvery {
            userSession.isUserLoggedIn()
        } returns false

        val startViewModel = StartViewModel(userSession)

        startViewModel.uiState.test {
            assertEquals(StartViewModel.UiState.Init, awaitItem())
            assertEquals(StartViewModel.UiState.NotConnected, awaitItem())
            cancel()
        }
    }
}
