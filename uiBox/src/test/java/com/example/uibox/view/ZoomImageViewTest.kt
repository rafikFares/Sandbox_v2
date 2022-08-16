package com.example.uibox.view

import com.example.uibox.BaseViewTest
import com.example.uibox.databinding.ViewImageZoomBinding
import com.squareup.picasso.Picasso
import kotlin.test.BeforeTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class ZoomImageViewTest : BaseViewTest<ZoomImageView, ViewImageZoomBinding>(ZoomImageView::class) {

    @BeforeTest
    fun before() {
        try {
            val picasso = Picasso.Builder(appContext).build()
            Picasso.setSingletonInstance(picasso)
        } catch (e: IllegalStateException) {
            // nothing controlled
        }
    }

    @Test
    fun initImageUrlIsSetCorrectly() {
        val value = "nothing"
        val zoomImageView = ZoomImageView(contextThemeWrapper)

        zoomImageView.initFullImage(value)

        val viewBinding = getBinding(zoomImageView)

        viewBinding shouldBeInstanceOf ViewImageZoomBinding::class.java
        zoomImageView.image shouldBe value
    }
}

