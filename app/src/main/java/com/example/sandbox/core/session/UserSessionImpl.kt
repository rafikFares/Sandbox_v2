package com.example.sandbox.core.session

import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.annotation.Single

@Single
class UserSessionImpl(
    private val preferenceRepository: PreferenceRepository,
) : UserSession {

    override suspend fun isUserLoggedIn(): Boolean {
        val token: String? = preferenceRepository.get(PreferenceKey.UserToken, "") as? String

        return suspendCancellableCoroutine<Boolean> { contiunuation ->
            val tokenIsValid = !token.isNullOrEmpty()
            if (contiunuation.isActive)
                contiunuation.resume(tokenIsValid)
        }
    }
}
