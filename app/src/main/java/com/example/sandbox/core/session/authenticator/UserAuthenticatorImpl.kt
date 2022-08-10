package com.example.sandbox.core.session.authenticator

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.sandbox.core.session.authenticator.UserAuthenticator
import com.example.sandbox.core.utils.Either
import org.koin.core.annotation.Single

@Single
class UserAuthenticatorImpl(private val preferenceRepository: PreferenceRepository) : UserAuthenticator {

    override suspend fun login(user: UserAuthenticator.User): Either<SandboxException, Boolean> {
        return try {
            // fake authentication
            check(user == UserAuthenticator.User.default) {
                "User is not Admin"
            }
            preferenceRepository.save(PreferenceKey.UserName, user.userName)
            preferenceRepository.save(PreferenceKey.UserToken, "${System.currentTimeMillis()}")
            Either.Success(true)
        } catch (e: Exception) {
            if (e is SandboxException) Either.Failure(e)
            Either.Failure(SandboxException.LoginException("$user"))
        }
    }

    override suspend fun logOut(): Either<SandboxException, Boolean> {
        preferenceRepository.delete(PreferenceKey.UserToken)
        preferenceRepository.delete(PreferenceKey.UserName)
        return Either.Success(true)
    }
}
