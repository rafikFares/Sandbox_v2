package com.example.sandbox.main.start

import app.cash.turbine.test
import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.MainDispatcherRule
import com.example.sandbox.core.session.UserSession
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class StartViewModelTest : BaseAndroidTest() {
    @MockK
    private lateinit var userSession: UserSession

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun userIsConnectedUpdateUiState() = runTest {
        coEvery {
            userSession.isUserLoggedIn()
        } returns true

        val startViewModel = StartViewModel(userSession)

        startViewModel.uiState.test {
            awaitItem() shouldBeEqualTo StartViewModel.UiState.Init
            awaitItem() shouldBeEqualTo StartViewModel.UiState.Connected
            cancel()
        }
    }

    @Test
    fun userIsNotConnectedUpdateUiState() = runTest {
        coEvery {
            userSession.isUserLoggedIn()
        } returns false

        val startViewModel = StartViewModel(userSession)

        startViewModel.uiState.test {
            awaitItem() shouldBeEqualTo StartViewModel.UiState.Init
            awaitItem() shouldBeEqualTo StartViewModel.UiState.NotConnected
            cancel()
        }
    }
}
