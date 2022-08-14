package com.example.sandbox.core.repository.remote

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.remote.model.NetworkItem
import com.example.sandbox.core.utils.Either

interface NetworkService {
    suspend fun retrieveItems(params: String? = null): Either<SandboxException, List<NetworkItem>>
}
