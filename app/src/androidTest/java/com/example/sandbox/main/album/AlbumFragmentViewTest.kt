package com.example.sandbox.main.album

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.sandbox.R
import com.example.sandbox.fragment.BaseAndroidUiFragmentTest
import com.example.sandbox.rule.DisableAnimationsRule
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class AlbumFragmentViewTest : BaseAndroidUiFragmentTest<AlbumFragment>() {

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    override val fragmentScenario: FragmentScenario<AlbumFragment>
        get() = launchFragmentInContainer(themeResId = R.style.Theme_Sandbox)

    @Test
    fun initialViewState() {
        onView(
            withId(R.id.albumProgress)
        ).check(
            matches(not(isDisplayed()))
        )

        onView(
            withId(R.id.containerLayout)
        ).check(
            matches(not(isDisplayed()))
        )
    }
}
