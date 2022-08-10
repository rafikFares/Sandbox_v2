package com.example.sandbox.main.alert

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import com.example.sandbox.BuildConfig
import com.example.sandbox.R
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.toString
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

object DefaultAlert {

    enum class AlertResult {
        Positive, Negative, Cancel
    }

    suspend fun create(
        context: Context,
        @StringSourceData title: StringSource = StringSource.Res(R.string.app_name),
        @StringSourceData message: StringSource,
        @StringSourceData positiveButtonText: StringSource = StringSource.Res(android.R.string.ok),
        @StringSourceData negativeButtonText: StringSource? = StringSource.Res(android.R.string.cancel),
        cancelable: Boolean = true,
        @StyleRes themeResId: Int = R.style.Theme_Sandbox_Alert
    ) = suspendCancellableCoroutine<AlertResult> { continuation ->
        if (Looper.myLooper() != Looper.getMainLooper()) {
            if (BuildConfig.DEBUG) Log.e("DefaultAlert", "You must call this method on the main thread")
            continuation.resume(AlertResult.Cancel)
        }
        val builder = AlertDialog.Builder(context, themeResId)

        // Title
        builder.setTitle(title.toString(context))

        // Message
        builder.setMessage(message.toString(context))

        builder.setPositiveButton(positiveButtonText.toString(context)) { _, _ ->
            continuation.resume(AlertResult.Positive)
        }

        negativeButtonText?.let {
            builder.setNegativeButton(it.toString(context)) { _, _ ->
                continuation.resume(AlertResult.Negative)
            }
        }

        builder.setCancelable(cancelable)
        if (cancelable) {
            builder.setOnCancelListener { continuation.resume(AlertResult.Cancel) }
        }

        builder.create().show()
    }
}
