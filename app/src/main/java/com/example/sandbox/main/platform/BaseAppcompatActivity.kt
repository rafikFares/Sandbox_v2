package com.example.sandbox.main.platform

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.main.alert.DefaultAlert
import com.example.uibox.tools.StringSource
import kotlinx.coroutines.launch

abstract class BaseAppcompatActivity : AppCompatActivity() {

    protected fun manageError(exception: SandboxException, okAction: (()-> Unit)? = null) {
        val message = when (exception) {
            SandboxException.NetworkConnectionException -> {
                "Network Connection Exception"
            }
            else -> {
                "Unmanaged Exception"
            }
        }

        lifecycleScope.launch {
            DefaultAlert.create(
                this@BaseAppcompatActivity,
                message = StringSource.String(message),
                negativeButtonText = null
            ).let {
                okAction?.invoke()
            }
        }
    }
}
