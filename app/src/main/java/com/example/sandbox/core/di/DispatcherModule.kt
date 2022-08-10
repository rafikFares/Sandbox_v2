package com.example.sandbox.core.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val dispatcherModule = module {
    single<CoroutineContext>(named("Dispatchers.IO")) {
        Dispatchers.IO
    }
    single<CoroutineContext>(named("Dispatchers.Default")) {
        Dispatchers.Default
    }
    single<CoroutineContext>(named("Dispatchers.Main")) {
        Dispatchers.Main
    }
    single<CoroutineContext>(named("Dispatchers.Unconfined")) {
        Dispatchers.Unconfined
    }
}
