package com.example.sandbox.rule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Disable emulator wifi and data connectivity
 */
class DisableNetworkRule(private val type: Type = Type.Auto) : TestWatcher() {

    enum class Type {
        Auto, // BeforeTest = Disable | AfterTest = Enable
        Manual // user should disable and enable manually
    }

    private val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    override fun starting(description: Description) {
        if (type == Type.Auto) {
            disableNetwork()
        }
    }

    override fun finished(description: Description) {
        if (type == Type.Auto) {
            enableNetwork()
        }
    }

    fun enableNetwork() {
        println(">>>enableNetwork>>>")
        with(uiDevice) {
            executeShellCommand("svc wifi enable")
            executeShellCommand("svc data enable")
            waitForIdle()
        }
        Thread.sleep(8000) // to apply effect | enabling network is slow
    }

    fun disableNetwork() {
        println(">>>disableNetwork>>>")
        with(uiDevice) {
            executeShellCommand("svc wifi disable")
            executeShellCommand("svc data disable")
        }
        Thread.sleep(1000) // to apply effect | disabling is fast
    }
}
