package com.example.sandbox.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module


private const val SANDBOX_PREFERENCES = "sandbox_preferences"

val appModule = module {

    single<DataStore<Preferences>> {
        val ioDispatcher: CoroutineContext = get(named("Dispatchers.IO"))
        providePreferencesDataStore(
            androidContext(),
            ioDispatcher
        )
    }

}

private fun providePreferencesDataStore(
    appContext: Context,
    ioDispatcher: CoroutineContext
): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        migrations = listOf(SharedPreferencesMigration(appContext, SANDBOX_PREFERENCES)),
        scope = CoroutineScope(ioDispatcher + SupervisorJob()),
        produceFile = { appContext.preferencesDataStoreFile(SANDBOX_PREFERENCES) }
    )
}
