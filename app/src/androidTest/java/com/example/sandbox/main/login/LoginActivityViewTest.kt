package com.example.sandbox.main.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import com.example.sandbox.BaseAndroidUiTest
import com.example.uibox.tools.StringSource
import org.junit.Test

@LargeTest
class LoginActivityViewTest : BaseAndroidUiTest<LoginActivity>(LoginActivity::class) {

    @Test
    fun top() {
        onView(
            withId(com.example.uibox.R.id.userNameContainer)
        ).check(
            matches(matchers.hasTextInputLayoutHintText(StringSource.Res(com.example.uibox.R.string.username)))
        )
        onView(
            withId(com.example.uibox.R.id.passwordContainer)
        ).check(
            matches(matchers.hasTextInputLayoutHintText(StringSource.Res(com.example.uibox.R.string.password)))
        )

        events.setText(com.example.uibox.R.id.userNameField, StringSource.String("Not Admin"))
        events.setText(com.example.uibox.R.id.passwordField, StringSource.String("12345678"))
        events.clickOnView(com.example.sandbox.R.id.loginButton)

        onView(
            withId(com.example.uibox.R.id.passwordContainer)
        ).check(
            matches(matchers.hasTextInputLayoutErrorText(StringSource.Res(com.example.sandbox.R.string.login_error_message)))
        )
    }
}
