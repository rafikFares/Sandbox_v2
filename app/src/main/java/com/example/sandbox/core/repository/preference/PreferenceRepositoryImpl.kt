package com.example.sandbox.core.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class PreferenceRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    @Named("Dispatchers.IO") private val ioDispatcher: CoroutineContext
) : PreferenceRepository {

    override suspend fun save(preferenceKey: PreferenceKey, value: String) {
        if (preferenceKey.isCorrectType(value)) {
            withContext(ioDispatcher) {
                dataStore.edit {
                    it[stringPreferencesKey(preferenceKey.keyName)] = value
                }
            }
        } else {
            throw SandboxException.PreferenceKeyException("Not correct type expected : ${preferenceKey.type} actual : ${value::class}")
        }
    }

    override suspend fun save(preferenceKey: PreferenceKey, value: Int) {
        if (preferenceKey.isCorrectType(value)) {
            withContext(ioDispatcher) {
                dataStore.edit {
                    it[intPreferencesKey(preferenceKey.keyName)] = value
                }
            }
        } else {
            throw SandboxException.PreferenceKeyException("Not correct type expected : ${preferenceKey.type} actual : ${value::class}")
        }
    }

    override suspend fun save(preferenceKey: PreferenceKey, value: Double) {
        if (preferenceKey.isCorrectType(value)) {
            withContext(ioDispatcher) {
                dataStore.edit {
                    it[doublePreferencesKey(preferenceKey.keyName)] = value
                }
            }
        } else {
            throw SandboxException.PreferenceKeyException("Not correct type expected : ${preferenceKey.type} actual : ${value::class}")
        }
    }

    override suspend fun save(preferenceKey: PreferenceKey, value: Boolean) {
        if (preferenceKey.isCorrectType(value)) {
            withContext(ioDispatcher) {
                dataStore.edit {
                    it[booleanPreferencesKey(preferenceKey.keyName)] = value
                }
            }
        } else {
            throw SandboxException.PreferenceKeyException("Not correct type expected : ${preferenceKey.type} actual : ${value::class}")
        }
    }

    override suspend fun save(preferenceKey: PreferenceKey, value: Float) {
        if (preferenceKey.isCorrectType(value)) {
            withContext(ioDispatcher) {
                dataStore.edit {
                    it[floatPreferencesKey(preferenceKey.keyName)] = value
                }
            }
        } else {
            throw SandboxException.PreferenceKeyException("Not correct type expected : ${preferenceKey.type} actual : ${value::class}")
        }
    }

    override suspend fun save(preferenceKey: PreferenceKey, value: Long) {
        if (preferenceKey.isCorrectType(value)) {
            withContext(ioDispatcher) {
                dataStore.edit {
                    it[longPreferencesKey(preferenceKey.keyName)] = value
                }
            }
        } else {
            throw SandboxException.PreferenceKeyException("Not correct type expected : ${preferenceKey.type} actual : ${value::class}")
        }
    }

    override suspend fun get(preferenceKey: PreferenceKey, defaultValue: Any): Any = withContext(ioDispatcher) {
        val allPreferences = dataStore.data.firstOrNull()?.asMap() ?: return@withContext defaultValue
        for (entry in allPreferences) {
            if (preferenceKey.keyName == entry.key.name) return@withContext entry.value
        }
        return@withContext defaultValue
    }

    override suspend fun delete(preferenceKey: PreferenceKey): Boolean = withContext(ioDispatcher) {
        val allPreferences = dataStore.data.firstOrNull()?.asMap() ?: emptyMap()
        for (entry in allPreferences) {
            if (preferenceKey.keyName == entry.key.name) {
                dataStore.edit {
                    it.remove(entry.key)
                }
                return@withContext true
            }
        }
        return@withContext false
    }

    override suspend fun clearAll(): Unit = withContext(ioDispatcher) {
        dataStore.edit {
            it.clear()
        }
    }
}
