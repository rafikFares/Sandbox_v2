package com.example.sandbox.main.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.sandbox.core.usecase.FetchAndStoreItemsUseCase
import com.example.sandbox.main.platform.BaseViewModel
import kotlin.coroutines.resume
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.android.annotation.KoinViewModel

private const val SEARCH_PARAMS = "technical-test.json"

@KoinViewModel(binds = [HomeViewModel::class])
class HomeViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val fetchAndStoreItemsUseCase: FetchAndStoreItemsUseCase
) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Updated : UiState
        object AlreadyUpToDate : UiState
        object Loading : UiState
        data class Error(val exception: SandboxException) : UiState
    }

    private val _uiState = MutableSharedFlow<UiState>()
    override val uiState: SharedFlow<UiState> = _uiState

    init {
        loadData()
    }

    @Suppress("SameParameterValue")
    private fun loadData() {
        updateUiState(UiState.Loading)
        viewModelScope.launch {
            val params = getSearchParams()
            fetchAndStoreItemsUseCase(params, viewModelScope) {
                it.fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    private fun handleSuccess(success: Boolean) {
        log("handleSuccess count : $success")
        if (success) {
            updateUiState(UiState.Updated)
        } else {
            updateUiState(UiState.AlreadyUpToDate)
        }
    }

    override fun handleFailure(failure: SandboxException) {
        super.handleFailure(failure)
        updateUiState(UiState.Error(failure))
    }

    private fun updateUiState(newState: UiState) {
        viewModelScope.launch {
            _uiState.emit(newState)
        }
    }

    private suspend fun getSearchParams(): String {
        val searchParams = preferenceRepository.get(
            PreferenceKey.ApiFileName,
            SEARCH_PARAMS
        ) as String // default with "technical-test.json" as params
        return suspendCancellableCoroutine {
            it.resume(searchParams)
        }
    }

    fun onRefreshClick() {
        loadData()
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("HomeViewModel", message, exception)
        }
    }
}
