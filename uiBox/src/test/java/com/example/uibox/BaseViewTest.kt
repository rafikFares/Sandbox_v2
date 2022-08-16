package com.example.uibox

import android.content.Context
import android.os.Build
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.core.app.ApplicationProvider
import androidx.viewbinding.ViewBinding
import io.mockk.MockKAnnotations
import java.lang.reflect.Field
import java.util.*
import kotlin.reflect.KClass
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O]
)
abstract class BaseViewTest<T : View, B : ViewBinding>(private val customViewClass: KClass<T>) {

    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = create(this@BaseViewTest)

    val appContext: Context
        get() = ApplicationProvider.getApplicationContext()

    val contextThemeWrapper = ContextThemeWrapper(appContext, R.style.MaterialTestingTheme)

    private fun create(testClass: Any) = TestRule { statement, _ ->
        MockKAnnotations.init(testClass, relaxUnitFun = true)
        statement
    }

    @Suppress("UNCHECKED_CAST")
    fun getBinding(customView: View): B? {
        return getFields()
            .firstOrNull { it.name == "binding" }
            ?.also { it.isAccessible = true }
            ?.get(customView) as? B
    }

    fun getFields(): kotlin.Array<out Field> {
        return customViewClass.java.declaredFields
    }
}
