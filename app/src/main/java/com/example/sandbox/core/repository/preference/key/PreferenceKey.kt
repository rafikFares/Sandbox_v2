package com.example.sandbox.core.repository.preference.key

import kotlin.reflect.KClass

enum class PreferenceKey(val keyName: String, val type: KClass<*>) {
    UserName("user_name", String::class),
    UserToken("user_token", String::class),
    LastFetch("last_fetch", Long::class);

    fun isCorrectType(other: Any) = other::class == type
}
