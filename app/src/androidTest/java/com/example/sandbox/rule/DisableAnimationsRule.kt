package com.example.sandbox.rule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Disable emulator animation during the test,
 * and re-enable animation back after the test
 */
class DisableAnimationsRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                // disable animations for test run
                changeAnimationStatus(enable = false)
                try {
                    base.evaluate()
                } finally {
                    // enable after test run
                    changeAnimationStatus(enable = true)
                }
            }
        }
    }

    private fun changeAnimationStatus(enable: Boolean = true) {
        val value = "${if (enable) 1 else 0}"
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand("settings put global transition_animation_scale $value")
            executeShellCommand("settings put global window_animation_scale $value")
            executeShellCommand("settings put global animator_duration_scale $value")
        }
    }
}
