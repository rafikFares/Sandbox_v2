package com.example.sandbox.core.repository.preference

import com.example.sandbox.core.repository.preference.key.PreferenceKey

interface PreferenceRepository {
    suspend fun save(preferenceKey: PreferenceKey, value: String)
    suspend fun save(preferenceKey: PreferenceKey, value: Int)
    suspend fun save(preferenceKey: PreferenceKey, value: Double)
    suspend fun save(preferenceKey: PreferenceKey, value: Boolean)
    suspend fun save(preferenceKey: PreferenceKey, value: Float)
    suspend fun save(preferenceKey: PreferenceKey, value: Long)
    suspend fun get(preferenceKey: PreferenceKey, defaultValue: Any): Any
    suspend fun delete(preferenceKey: PreferenceKey): Boolean // true is found and deleted else false
    suspend fun clearAll()
}
