package com.example.sandbox.core.session.authenticator

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf

class UserAuthenticatorImplTest : BaseUnitTest() {
    @MockK
    private lateinit var preferenceRepository: PreferenceRepository

    private lateinit var userAuthenticator: UserAuthenticator

    @BeforeTest
    fun setUp() {
        userAuthenticator = UserAuthenticatorImpl(preferenceRepository)
        coEvery {
            preferenceRepository.save(any(), any() as String)
        } returns Unit
    }

    @Test
    fun testLoginUserAdminSuccess() = runBlocking {
        val adminUser = UserAuthenticator.User.default
        val result = userAuthenticator.login(adminUser)

        result shouldBeInstanceOf Either.Success::class.java
    }

    @Test
    fun testLoginUserException(): Unit = runBlocking {
        val adminUser = UserAuthenticator.User("aaaaa", "bbbbb")
        val result = userAuthenticator.login(adminUser)

        result shouldBeInstanceOf Either.Failure::class.java
        val exception = (result as Either.Failure).value
        exception shouldBeEqualTo SandboxException.LoginException("$adminUser")
    }

    @Test
    fun testLogOutUserSuccess() = runBlocking {
        coEvery {
            preferenceRepository.delete(any())
        } returns true
        val adminUser = UserAuthenticator.User.default
        userAuthenticator.login(adminUser)

        val result = userAuthenticator.logOut()

        result shouldBeInstanceOf Either.Success::class.java
    }
}
