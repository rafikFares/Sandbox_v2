package com.example.sandbox.core.pagging

import androidx.paging.PagingSource
import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.data.AlbumItem
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf

@ExperimentalCoroutinesApi
class AlbumItemPagingSourceTest : BaseUnitTest() {

    @MockK
    private lateinit var localRepository: LocalRepository
    private lateinit var albumItemPagingSource: AlbumItemPagingSource

    @BeforeTest
    fun before() {
        albumItemPagingSource = AlbumItemPagingSource(localRepository)
    }

    @Test
    fun loadDataWithRangeSuccess() = runTest {
        val items = listOf(AlbumItem(0, 0), AlbumItem(1, 1))
        val success = Either.Success(items)
        coEvery {
            localRepository.retrieveAlbums(any())
        } returns success

        val result = albumItemPagingSource.loadData(IntRange.EMPTY)

        result shouldBeInstanceOf Either.Success::class
        (result as Either.Success).value shouldBeEqualTo items
    }

    @Test
    fun loadDataWithRangeFail() = runTest {
        val failure = Either.Failure(SandboxException.DatabaseErrorException())
        coEvery {
            localRepository.retrieveAlbums(any())
        } returns failure

        val result = albumItemPagingSource.loadData(IntRange.EMPTY)

        result shouldBeInstanceOf Either.Failure::class
        (result as Either.Failure).value shouldBeInstanceOf SandboxException.DatabaseErrorException::class
    }

    @Test
    fun defaultLoadReturnsPage() = runTest {
        val items = listOf(AlbumItem(0, 0), AlbumItem(1, 1))
        val success = Either.Success(items)
        coEvery {
            localRepository.retrieveAlbums(any())
        } returns success

        val result = albumItemPagingSource.load(PagingSource.LoadParams.Refresh(null, 50, false))

        result shouldBeInstanceOf PagingSource.LoadResult.Page::class
        val page = result as PagingSource.LoadResult.Page
        page.nextKey shouldBeEqualTo 2
        page.prevKey shouldBeEqualTo null
        page.data shouldBeEqualTo items
    }
}
