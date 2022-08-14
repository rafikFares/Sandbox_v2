package com.example.sandbox

import android.view.View
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.toString
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

class BaseMatchers : Matchers() {
    /**
     * assert TextInputLayout error text
     */
    fun hasTextInputLayoutErrorText(@StringSourceData expectedErrorStringSource: StringSource): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View?): Boolean {
                if (item !is TextInputLayout) return false
                val error = item.error ?: return false
                return expectedErrorStringSource.toString(item.context) == "$error"
            }

            override fun describeTo(description: Description?) {
            }
        }

    /**
     * assert TextInputLayout hint text
     */
    fun hasTextInputLayoutHintText(@StringSourceData expectedHintStringSource: StringSource): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View?): Boolean {
                if (item !is TextInputLayout) return false
                val hint = item.hint ?: return false
                return expectedHintStringSource.toString(item.context) == "$hint"
            }

            override fun describeTo(description: Description?) {
            }
        }
}
