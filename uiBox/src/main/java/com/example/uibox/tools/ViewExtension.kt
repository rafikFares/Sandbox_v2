package com.example.uibox.tools

import android.os.SystemClock
import android.view.View

enum class ScaleType(val value: Float) {
    Small(1.1F), Medium(1.2F), Big(1.3F), Extra(1.5F)
}

fun View.animateClick(scaleType: ScaleType = ScaleType.Small, endTask: (() -> Unit)? = null) {
    apply {
        animate()
            .setDuration(100L)
            .scaleX(scaleType.value)
            .scaleY(scaleType.value)
            .withEndAction {
                this.scaleX = 1f
                this.scaleY = 1f
                endTask?.invoke()
            }
            .start()
    }
}

fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
