package com.example.uibox.view

import com.example.uibox.BaseViewTest
import com.example.uibox.databinding.ViewImageZoomBinding
import com.example.uibox.tools.ImageSource
import com.squareup.picasso.Picasso
import io.mockk.spyk
import io.mockk.verify
import kotlin.test.BeforeTest
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
    fun viewBindingCorrectly() {
        val zoomImageView = ZoomImageView(contextThemeWrapper)

        val viewBinding = getBinding(zoomImageView)

        viewBinding shouldBeInstanceOf ViewImageZoomBinding::class.java
    }

    @Test
    fun initImageUrlIsSetCorrectly() {
        val value = "nothing"
        val zoomImageView = spyk(ZoomImageView(contextThemeWrapper))

        zoomImageView.initFullImage(value)

        verify(exactly = 1) {
            zoomImageView.setImage(ImageSource.Url(value))
        }
    }
}

