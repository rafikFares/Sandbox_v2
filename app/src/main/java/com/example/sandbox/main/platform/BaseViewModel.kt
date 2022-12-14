package com.example.sandbox.main.platform

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sandbox.core.exception.SandboxException
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    interface BaseUiState

    abstract val uiState: SharedFlow<BaseUiState?>

    private val _failure: MutableLiveData<LifecycleEvent<SandboxException>> = MutableLiveData()
    val failure: LiveData<LifecycleEvent<SandboxException>> = _failure

    protected abstract fun log(message: String, exception: Exception? = null)

    protected open fun handleFailure(failure: SandboxException) {
        log("handleFailure", failure)
        _failure.value = LifecycleEvent(failure)
    }
}
