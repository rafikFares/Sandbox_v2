package com.example.uibox.view

import com.example.uibox.BaseViewTest
import com.example.uibox.databinding.ViewAlertInformationBinding
import com.example.uibox.tools.ColorSource
import com.example.uibox.tools.DrawableSource
import com.example.uibox.tools.StringSource
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert


class InformationAlertViewTest : BaseViewTest<InformationAlertView, ViewAlertInformationBinding>(InformationAlertView::class) {

    private lateinit var informationAlertView: InformationAlertView

    @BeforeTest
    fun before() {
        informationAlertView = InformationAlertView(contextThemeWrapper)
    }

    @Test
    fun informationTextIsSetCorrectly() {
        val value = "example test 1"
        val valueStringSource = StringSource.String(value)

        informationAlertView.configure(
            InformationAlertView.Information(valueStringSource)
        )

        val viewBinding = getBinding(informationAlertView)

        viewBinding shouldBeInstanceOf ViewAlertInformationBinding::class.java
        viewBinding!!.informationText.text shouldBeEqualTo value
    }

    @Test
    fun informationDataIsSetCorrectly() {
        val value = "example test 2"
        val valueStringSource = StringSource.String(value)
        val valueDrawable = com.example.uibox.R.drawable.ic_baseline_lock
        val valueColor = com.example.uibox.R.color.color_blue
        val informationDate = InformationAlertView.Information(
            text = valueStringSource,
            icon = DrawableSource.Res(valueDrawable),
            backgroundColor = ColorSource.Res(valueColor)
        )

        informationAlertView.configure(informationDate)

        val viewBinding = getBinding(informationAlertView)

        viewBinding!!.informationText.text shouldBeEqualTo value
        Assert.assertNotNull(viewBinding.informationIcon.drawable)
    }
}
