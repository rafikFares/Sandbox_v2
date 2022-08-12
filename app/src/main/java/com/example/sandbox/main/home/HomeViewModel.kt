package com.example.sandbox.main.home

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.usecase.FetchAndStoreItemsUseCase
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val SEARCH_PARAMS = "technical-test.json"

@KoinViewModel(binds = [HomeViewModel::class])
class HomeViewModel(
    private val fetchAndStoreItemsUseCase: FetchAndStoreItemsUseCase,
) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        object Complete : UiState
        object Loading : UiState
        data class Error(val exception: SandboxException) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    override val uiState: StateFlow<UiState> = _uiState

    override fun onCreate(owner: LifecycleOwner) {
        println(">>>>>>>>>>> onCreate")
        loadData(SEARCH_PARAMS) // default with "text" as string
    }

    @Suppress("SameParameterValue")
    private fun loadData(params: String) {
        updateUiState(UiState.Loading)
        fetchAndStoreItemsUseCase(params, viewModelScope) {
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(success: Boolean) {
        log("handleSuccess count : $success")
        updateUiState(UiState.Complete)
    }

    override fun handleFailure(failure: SandboxException) {
        super.handleFailure(failure)
        updateUiState(UiState.Error(failure))
    }

    private fun updateUiState(newState: UiState) {
        viewModelScope.launch {
            _uiState.value = newState
        }
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("HomeViewModel", message, exception)
        }
    }
}
