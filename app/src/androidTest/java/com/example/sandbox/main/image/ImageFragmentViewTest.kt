package com.example.sandbox.main.image

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.sandbox.BaseAndroidUiFragmentTest
import com.example.sandbox.R
import org.hamcrest.Matchers.not
import org.junit.Test

class ImageFragmentViewTest : BaseAndroidUiFragmentTest<ImageFragment>() {

    override val fragmentScenario: FragmentScenario<ImageFragment>
        get() = launchFragmentInContainer(themeResId = R.style.Theme_Sandbox)

    @Test
    fun initialViewState() {
        onView(
            withId(R.id.imageProgress)
        ).check(
            matches(not(isDisplayed()))
        )

        onView(
            withId(R.id.fullImage)
        ).check(
            matches(not(isDisplayed()))
        )
    }
}
