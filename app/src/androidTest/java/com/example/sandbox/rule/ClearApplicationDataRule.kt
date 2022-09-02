package com.example.sandbox.rule

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Clear android application user data
 */
class ClearApplicationDataRule(private val packageName: String) : TestWatcher() {

    private val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    override fun starting(description: Description) {
        println(">>>clearAllData>>>")
        clearAllData()
    }

    override fun finished(description: Description) {
        // nothing
    }

    fun clearAllData() {
        uiDevice.executeShellCommand("pm clear $packageName")
    }

    companion object {
        operator fun invoke(context: Context): ClearApplicationDataRule = ClearApplicationDataRule(context.packageName)
    }
}
