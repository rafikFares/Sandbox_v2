package com.example.sandbox.core.api

import com.example.sandbox.core.repository.remote.data.ApiGitHubItemDetail
import com.example.sandbox.core.repository.remote.data.ApiGitHubItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {
    @GET("shared/{fileName}")
    suspend fun fetchImages(
        @Path(value = "fileName", encoded = true) fileName: String
    ): Response<ApiGitHubItemDetail>
}

