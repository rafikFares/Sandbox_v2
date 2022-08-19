package com.example.sandbox.core.session.authenticator

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.utils.Either

interface UserAuthenticator {

    data class User(
        val userName: String,
        val password: String
    ) {
        companion object {
            val default = User(
                userName = "admin",
                password = "admin"
            )
        }
    }

    suspend fun login(user: User): Either<SandboxException, Boolean>
    suspend fun logOut(): Either<SandboxException, Boolean>
}
