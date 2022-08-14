package com.example.sandbox.main.image

import app.cash.turbine.test
import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.MainDispatcherRule
import com.example.sandbox.core.repository.local.LocalRepository
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class ImageViewModelTest : BaseAndroidTest() {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var localRepository: LocalRepository
    private lateinit var imageViewModel: ImageViewModel

    @BeforeTest
    fun setUp() {
        imageViewModel = ImageViewModel(localRepository)
    }

    @Test
    fun onImageItemClickUpdateClickedImageUrl() {
        val imageUrl = "imageUrl"

        imageViewModel.clickedImageUrl.observeForever {
            it shouldBeEqualTo imageUrl
        }

        imageViewModel.onImageItemClick(imageUrl)
    }

    @Test
    fun onImageItemClickUpdateUiState() = runTest(mainDispatcherRule.testDispatcher) {
        imageViewModel.uiState.test {
            imageViewModel.onImageItemClick("")
            awaitItem() shouldBe ImageViewModel.UiState.ShowImage
            cancel()
        }
    }
}
