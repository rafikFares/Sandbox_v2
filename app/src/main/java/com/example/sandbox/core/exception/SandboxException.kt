package com.example.sandbox.core.exception

sealed class SandboxException: Exception() {
    data class PreferenceKeyException(override val message: String) : SandboxException()
    data class LoginException(val user:String) : SandboxException()
    object EmptyParamsException : SandboxException()
    object NetworkConnectionException : SandboxException()
    data class ServerErrorException(override val message: String? = null) : SandboxException()
    data class DatabaseErrorException(override val message: String? = null) : SandboxException()
    data class ElementNotFoundException(val elementName:String, override val message: String? = null) : SandboxException()
}
