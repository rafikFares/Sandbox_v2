package com.example.sandbox.main.start

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.session.UserSession
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [StartViewModel::class])
class StartViewModel(private val userSession: UserSession) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        object Connected : UiState
        object NotConnected : UiState
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Init)
    override val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            log("checking if user is logged in")
            delay(500) // just to mock the request timing
            if (userSession.isUserLoggedIn()) {
                _uiState.value = UiState.Connected
            } else {
                _uiState.value = UiState.NotConnected
            }
        }
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("StartViewModel", message, exception)
        }
    }
}
