package com.example.sandbox.core.api

import com.example.sandbox.core.repository.remote.model.ApiItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {
    @GET("shared/{fileName}")
    suspend fun fetchImages(
        @Path(value = "fileName", encoded = true) fileName: String
    ): Response<List<ApiItem>>
}

