package com.example.sandbox

import android.app.Activity
import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlin.reflect.KClass
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseAndroidUiTest<T : Activity>(activity: KClass<T>) {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(activity.java)

    @Before
    fun intentsInit() {
        // initialize Espresso Intents capturing
        Intents.init()
    }

    @After
    fun intentsTeardown() {
        // release Espresso Intents capturing
        Intents.release()
    }

    protected val appContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    protected val scenario: ActivityScenario<T>
        get() = activityScenarioRule.scenario

    protected val matchers: BaseMatchers = BaseMatchers()
    protected val events: BaseEvents = BaseEvents()
}
