package com.example.sandbox.core.pagging

import androidx.paging.PagingSource
import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageItemPagingSourceTest : BaseUnitTest() {

    @MockK
    private lateinit var localRepository: LocalRepository
    private lateinit var imageItemPagingSource: ImageItemPagingSource

    @BeforeTest
    fun before() {
        imageItemPagingSource = ImageItemPagingSource(localRepository)
    }

    @Test
    fun loadDataWithRangeSuccess() = runTest {
        val items = listOf(
            ImageItem(0, 0, "", "", ""),
            ImageItem(1, 1, "", "", "")
        )
        val success = Either.Success(items)
        coEvery {
            localRepository.retrieveItems(any())
        } returns success

        val result = imageItemPagingSource.loadData(IntRange.EMPTY)

        result shouldBeInstanceOf Either.Success::class
        (result as Either.Success).value shouldBeEqualTo items
    }

    @Test
    fun loadDataWithRangeFail() = runTest {
        val failure = Either.Failure(SandboxException.DatabaseErrorException())
        coEvery {
            localRepository.retrieveItems(any())
        } returns failure

        val result = imageItemPagingSource.loadData(IntRange.EMPTY)

        result shouldBeInstanceOf Either.Failure::class
        (result as Either.Failure).value shouldBeInstanceOf SandboxException.DatabaseErrorException::class
    }

    @Test
    fun defaultLoadReturnsPage() = runTest {
        val items = listOf(
            ImageItem(0, 0, "", "", ""),
            ImageItem(1, 1, "", "", "")
        )
        val success = Either.Success(items)
        coEvery {
            localRepository.retrieveItems(any())
        } returns success

        val result = imageItemPagingSource.load(PagingSource.LoadParams.Refresh(null, 50, false))

        result shouldBeInstanceOf PagingSource.LoadResult.Page::class
        val page = result as PagingSource.LoadResult.Page
        page.nextKey shouldBeEqualTo 2
        page.prevKey shouldBeEqualTo null
        page.data shouldBeEqualTo items
    }
}
