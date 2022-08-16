package com.example.uibox.view

import com.example.uibox.BaseViewTest
import com.example.uibox.databinding.ViewImageItemBinding
import com.example.uibox.tools.Empty
import com.example.uibox.tools.StringSource
import com.squareup.picasso.Picasso
import kotlin.test.BeforeTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class ItemImageViewTest : BaseViewTest<ItemImageView, ViewImageItemBinding>(ItemImageView::class) {

    private lateinit var itemImageView: ItemImageView

    @BeforeTest
    fun before() {
        try {
            val picasso = Picasso.Builder(appContext).build()
            Picasso.setSingletonInstance(picasso)
        } catch (e: IllegalStateException) {
            // nothing controlled
        }
        itemImageView = ItemImageView(contextThemeWrapper)
    }

    @Test
    fun imageDataIsSetCorrectly() {
        val imageId = "imageId"
        val imageAlbumId = "imageAlbumId"
        val imageTitle = "imageTitle"
        val imageData = ItemImageView.ImageData(
            imageAlbumId = StringSource.String(imageAlbumId),
            imageId = StringSource.String(imageId),
            imageTitle = StringSource.String(imageTitle),
            imageThumbnailUrl = "url",
            imageUrl = "url"
        )

        itemImageView.configure(imageData, {})

        val viewBinding = getBinding(itemImageView)

        viewBinding shouldBeInstanceOf ViewImageItemBinding::class.java
        viewBinding!!.imageId.text shouldBe imageId
        viewBinding.imageTitle.text shouldBe imageTitle
    }

    @Test
    fun performClickSuccess() {
        val imageData = ItemImageView.ImageData(
            imageAlbumId = Empty,
            imageId = Empty,
            imageTitle = Empty,
            imageThumbnailUrl = "url",
            imageUrl = "url"
        )

        itemImageView.init(imageData) {
            it shouldBeEqualTo imageData
        }

        val viewBinding = getBinding(itemImageView)
        viewBinding!!.imageIcon.performClick()
    }
}
