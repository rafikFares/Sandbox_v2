package com.example.sandbox.core.di

import com.example.sandbox.BuildConfig
import com.example.sandbox.core.api.ServiceApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB
private const val DEFAULT_TIMEOUT = 15L // seconds
private const val CONTENT_TYPE = "application/json"

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {

    // provide HttpLoggingInterceptor
    single<HttpLoggingInterceptor> {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        logging
    }

    // provide OkHttpClient
    single<OkHttpClient> {
        val httpLoggingInterceptor: HttpLoggingInterceptor = get()
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor) // Logging Interceptor
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS) // Timeout
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS) // Timeout
            .cache(Cache(File(androidContext().cacheDir, "okhttp"), CACHE_SIZE)) // Cache
            .build()
    }

    // provide default json
    single<Json> {
        fun provideJson(
            _prettyPrint: Boolean = false,
            _encodeDefaults: Boolean = false
        ) = Json {
            prettyPrint = _prettyPrint
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = _encodeDefaults
            useAlternativeNames = false
        }

        provideJson(true, true)
    }

    // provide Retrofit
    single<Retrofit> {
        val defaultJson: Json = get()
        val okHttpClient: OkHttpClient = get()
        val contentType = CONTENT_TYPE.toMediaType()
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(defaultJson.asConverterFactory(contentType))
            .build()
    }

    // provide Api
    single<ServiceApi> {
        val retrofit: Retrofit = get()
        retrofit.create(ServiceApi::class.java)
    }
}
