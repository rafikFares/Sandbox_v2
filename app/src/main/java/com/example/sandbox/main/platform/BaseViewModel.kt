package com.example.sandbox.main.platform

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sandbox.core.exception.SandboxException
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    interface BaseUiState

    abstract val uiState: StateFlow<BaseUiState?>

    private val _failure: MutableLiveData<SandboxException> = MutableLiveData()
    val failure: LiveData<SandboxException> = _failure

    protected abstract fun log(message: String, exception: Exception? = null)

    protected open fun handleFailure(failure: SandboxException) {
        log("handleFailure", failure)
        _failure.value = failure
    }
}
