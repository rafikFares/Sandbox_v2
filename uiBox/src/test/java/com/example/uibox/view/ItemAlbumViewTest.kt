package com.example.uibox.view

import com.example.uibox.BaseViewTest
import com.example.uibox.databinding.ViewAlbumItemBinding
import com.example.uibox.tools.Empty
import com.example.uibox.tools.StringSource
import kotlin.test.BeforeTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class ItemAlbumViewTest : BaseViewTest<ItemAlbumView, ViewAlbumItemBinding>(ItemAlbumView::class) {
    private lateinit var itemAlbumView: ItemAlbumView

    @BeforeTest
    fun before() {
        itemAlbumView = ItemAlbumView(contextThemeWrapper)
    }

    @Test
    fun albumDataIsSetCorrectly() {
        val albumId = "1"
        val albumImagesCount = "55"
        val albumData = ItemAlbumView.AlbumData(
            albumId = StringSource.String(albumId),
            albumImagesCount = StringSource.String(albumImagesCount)
        )

        itemAlbumView.configure(albumData, {})

        val viewBinding = getBinding(itemAlbumView)

        viewBinding shouldBeInstanceOf ViewAlbumItemBinding::class.java
        viewBinding!!.albumId.text shouldBe albumId
        viewBinding.albumItemsCount.text shouldBe albumImagesCount
    }

    @Test
    fun performClickSuccess() {
        val albumData = ItemAlbumView.AlbumData(
            albumId = Empty,
            albumImagesCount = Empty
        )

        itemAlbumView.init(albumData) {
            it shouldBeEqualTo albumData
        }

        val viewBinding = getBinding(itemAlbumView)
        viewBinding!!.albumId.performClick()
    }
}
