package com.example.uibox.tools

import android.os.SystemClock
import android.view.View

fun View.animateClick(endTask: (() -> Unit)? = null) {
    apply {
        animate()
            .setDuration(100L)
            .scaleX(1.3f)
            .scaleY(1.3f)
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
