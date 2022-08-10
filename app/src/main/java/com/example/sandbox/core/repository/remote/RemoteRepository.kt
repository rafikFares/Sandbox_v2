package com.example.sandbox.core.repository.remote

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.remote.model.ApiItem
import com.example.sandbox.core.utils.Either

interface RemoteRepository {
    suspend fun retrieveItems(params: String? = null): Either<SandboxException, List<ApiItem>>
}
