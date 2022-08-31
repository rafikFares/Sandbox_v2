package com.example.sandbox.fragment

import android.content.Context
import android.util.Log
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sandbox.R
import com.example.sandbox.events.BaseEvents
import com.example.sandbox.matcher.BaseMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * a better implementation of
 * https://github.com/android/architecture-components-samples/blob/8f4936b34ec84f7f058fba9732b8692e97c65d8f/GithubBrowserSample/app/src/androidTest/java/com/android/example/github/util/DataBindingIdlingResourceTest.kt
 */
@RunWith(AndroidJUnit4::class)
abstract class BaseAndroidUiFragmentTest<F : Fragment> {
    private val idlingResource = FragmentDataBindingIdlingResource<F>()

    @get:Rule
    val fragmentDataBindingIdlingResourceRule = FragmentDataBindingIdlingResourceRule(idlingResource)

    abstract val fragmentScenario: FragmentScenario<F>?

    @Before
    fun init() {
        checkNotNull(fragmentScenario) {
            Log.e("BaseAndroidUiFragmentTest", "Need to override fragmentScenario")
        }
        idlingResource.monitorFragment(fragmentScenario!!)
        Espresso.onIdle()
    }

    @StyleRes
    protected val defaultThemeResId: Int = R.style.Theme_Sandbox

    protected val appContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    protected val matchers: BaseMatchers = BaseMatchers()
    protected val events: BaseEvents = BaseEvents()
    protected fun getFragment(): F? {
        return idlingResource.fragment
    }
}
