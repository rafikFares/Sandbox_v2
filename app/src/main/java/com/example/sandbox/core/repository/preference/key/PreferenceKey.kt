package com.example.sandbox.core.repository.preference.key

import kotlin.reflect.KClass

enum class PreferenceKey(val keyName: String, val type: KClass<*>) {
    UserName("user_name", String::class),
    UserToken("user_token", String::class),
    UserAge("user_age", Int::class);

    fun isCorrectType(other: Any) = other::class == type
}
