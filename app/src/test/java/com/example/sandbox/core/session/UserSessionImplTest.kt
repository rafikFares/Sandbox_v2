package com.example.sandbox.core.session

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.sandbox.core.utils.empty
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.Test


class UserSessionImplTest: BaseUnitTest() {

    @MockK
    private lateinit var preferenceRepository: PreferenceRepository

    private lateinit var userSession: UserSession

    @BeforeTest
    fun setUp() {
        userSession = UserSessionImpl(preferenceRepository)
    }

    @Test
    fun testUserLoggedInSuccess() {
        coEvery {
            preferenceRepository.get(PreferenceKey.UserToken, any())
        } returns "not empty"

        val result = runBlocking {
            userSession.isUserLoggedIn()
        }

        result shouldBe true
    }

    @Test
    fun testUserLoggedInFail() {
        coEvery {
            preferenceRepository.get(PreferenceKey.UserToken, any())
        } returns String.empty()

        val result = runBlocking {
            userSession.isUserLoggedIn()
        }

        result shouldBe false
    }
}
