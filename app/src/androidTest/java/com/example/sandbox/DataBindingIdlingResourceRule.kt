package com.example.sandbox

import androidx.fragment.app.Fragment
import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A JUnit rule that registers an idling resource for all fragment views that use data binding.
 */
class DataBindingIdlingResourceRule<F : Fragment>(
    private val idlingResource: DataBindingIdlingResource<F>
) : TestWatcher() {

    override fun finished(description: Description) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }
}
