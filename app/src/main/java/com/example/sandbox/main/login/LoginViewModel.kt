package com.example.sandbox.main.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.session.authenticator.UserAuthenticator
import com.example.sandbox.core.utils.Either
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [LoginViewModel::class])
class LoginViewModel(private val userAuthenticator: UserAuthenticator) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        object LoginSuccess : UiState
        object LoginError : UiState
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Init)
    override val uiState: StateFlow<BaseUiState>
        get() = _uiState

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            val user = UserAuthenticator.User(userName = userName, password = password)
            val result = userAuthenticator.login(user)
            when (result) {
                is Either.Failure -> {
                    log("Failurer", result.value)
                    _uiState.value = UiState.LoginError
                    handleFailure(result.value)
                }
                is Either.Success -> {
                    _uiState.value = UiState.LoginSuccess
                }
            }
        }
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("LoginViewModel", message, exception)
        }
    }
}
