package com.example.sandbox.main.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import com.example.sandbox.BaseAndroidUiTest
import com.example.sandbox.R
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.toString
import org.junit.Test

@LargeTest
class HomeActivityViewTest : BaseAndroidUiTest<HomeActivity>(HomeActivity::class) {

    @Test
    fun defaultViewInteraction() {
        // refresh lottie is animating
        onView(
            withId(R.id.refreshButton)
        ).check(
            matches(matchers.isLottieAnimationAnimating())
        )

        // on refresh click
        events.clickOnView(R.id.refreshButton)

        // show DefaultAlert with no network exception
        val dialogText = StringSource.Res(R.string.app_name).toString(appContext)
        onView(withText(dialogText)).check(matches(isDisplayed()))
    }
}
