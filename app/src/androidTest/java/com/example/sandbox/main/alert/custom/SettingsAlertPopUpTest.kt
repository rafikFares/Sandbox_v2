package com.example.sandbox.main.alert.custom

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import com.example.sandbox.BaseAndroidUiFragmentTest
import com.example.sandbox.R
import com.example.sandbox.rule.DisableAnimationsRule
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SettingsAlertPopUpTest : BaseAndroidUiFragmentTest<SettingsAlertPopUp>() {

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    override val fragmentScenario: FragmentScenario<SettingsAlertPopUp>
        get() = launchFragment(themeResId = R.style.Theme_Sandbox)

    @Test
    fun testDismissDialogFragment() {
        with(launchFragment<SettingsAlertPopUp>(themeResId = R.style.Theme_Sandbox)) {
            onFragment { fragment ->
                Assert.assertTrue(fragment.dialog != null)
                Assert.assertTrue(fragment.requireDialog().isShowing)
                fragment.dismiss()
                fragment.parentFragmentManager.executePendingTransactions()
                Assert.assertTrue(fragment.dialog == null)
            }
        }
    }
}
