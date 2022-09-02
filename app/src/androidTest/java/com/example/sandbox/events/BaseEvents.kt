package com.example.sandbox.events

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.applyStringSource
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

class BaseEvents {
    fun clickOnViewId(@IdRes viewId: Int) {
        onView(withId(viewId)).perform(click())
    }

    fun clickOnViewWithText(text: String) {
        onView(withText(text)).perform(click())
    }

    fun clickOnViewWithTextResId(@IdRes text: Int) {
        onView(withText(text)).perform(click())
    }

    /**
     * set StringSourceData to any view which extends TextView
     */
    fun setText(@IdRes viewId: Int, @StringSourceData textSource: StringSource): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String = "replace text"

            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(TextView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as? TextView)?.applyStringSource(textSource)
            }
        }
    }
}
