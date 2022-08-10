package com.example.sandbox.core.session

interface UserSession {
    suspend fun isUserLoggedIn(): Boolean
}
