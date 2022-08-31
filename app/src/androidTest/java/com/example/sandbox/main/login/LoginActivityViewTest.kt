package com.example.sandbox.main.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.sandbox.activity.BaseAndroidUiActivityTest
import com.example.sandbox.rule.DisableAnimationsRule
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.toString
import org.junit.Test

class LoginActivityViewTest : BaseAndroidUiActivityTest<LoginActivity>(LoginActivity::class) {

    private val animationRule = DisableAnimationsRule() // the first test need animation, that's why it is not used as Rule here

    @Test
    fun defaultViewInteraction() {
        onView(
            withId(com.example.sandbox.R.id.animation_view)
        ).check(
            matches(matchers.isLottieAnimationAnimating())
        )
    }

    @Test
    fun passwordErrorToTryAdminAdmin() {
        try {
            animationRule.disableUiAnimation()

            onView(
                withId(com.example.uibox.R.id.userNameContainer)
            ).check(
                matches(
                    matchers.hasTextInputLayoutHintText(
                        StringSource.Res(com.example.uibox.R.string.username)
                    )
                )
            )
            onView(
                withId(com.example.uibox.R.id.passwordContainer)
            ).check(
                matches(
                    matchers.hasTextInputLayoutHintText(
                        StringSource.Res(com.example.uibox.R.string.password)
                    )
                )
            )

            events.setText(com.example.uibox.R.id.userNameField, StringSource.String("Not Admin"))
            events.setText(com.example.uibox.R.id.passwordField, StringSource.String("12345678"))
            events.clickOnView(com.example.sandbox.R.id.loginButton)

            onView(
                withId(com.example.uibox.R.id.passwordContainer)
            ).check(
                matches(
                    matchers.hasTextInputLayoutErrorText(
                        StringSource.Res(com.example.sandbox.R.string.login_error_message)
                    )
                )
            )
        } finally {
            animationRule.enableUiAnimation()
        }
    }

    @Test
    fun viewSettingsInteraction() {
        try {
            animationRule.disableUiAnimation()

            events.clickOnView(com.example.sandbox.R.id.settings)

            events.setText(com.example.uibox.R.id.alertTextField, StringSource.String("false_regex"))
            events.clickOnView(
                StringSource.Res(com.example.uibox.R.string.confirm).toString(appContext)
            )

            onView(
                withId(com.example.uibox.R.id.alertTextFieldContainer)
            ).check(
                matches(
                    matchers.hasTextInputLayoutErrorText(
                        StringSource.Res(com.example.sandbox.R.string.settings_fail_message)
                    )
                )
            )
        } finally {
            animationRule.enableUiAnimation()
        }
    }
}
