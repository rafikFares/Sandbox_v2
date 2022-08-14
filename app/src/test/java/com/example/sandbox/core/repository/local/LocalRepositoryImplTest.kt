package com.example.sandbox.core.repository.local

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.data.AlbumItem
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf

@ExperimentalCoroutinesApi
class LocalRepositoryImplTest : BaseUnitTest() {
    private val unconfinedDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var itemDao: ItemDao

    private lateinit var localRepository: LocalRepository

    @BeforeTest
    fun before() {
        localRepository = LocalRepositoryImpl(itemDao, unconfinedDispatcher)
    }

    @Test
    fun insertItemsSuccess() = runTest {
        val result = localRepository.insertItems(emptyList())
        result shouldBeInstanceOf Either.Success::class.java
        (result as Either.Success).value shouldBe true
        coVerify(exactly = 1) {
            itemDao.insertItems(emptyList())
        }
    }

    @Test
    fun retrieveItemsCallIntRange() = runTest {
        val range = IntRange(0, 10)
        val items = listOf(ItemEntity())
        coEvery {
            itemDao.retrieveAllItemsInRange(range)
        } returns items

        val tmp = localRepository.retrieveItems(range.first, range.last)

        tmp shouldBeInstanceOf Either.Success::class
        (tmp as Either.Success).value.first().id shouldBeEqualTo items.first().id
    }

    @Test
    fun retrieveItemsRangeSuccess() = runTest {
        val range = IntRange(0, 10)
        coEvery {
            itemDao.retrieveAllItemsInRange(range)
        } returns emptyList()
        val result = localRepository.retrieveItems(range)

        coVerify(exactly = 1) {
            itemDao.retrieveAllItemsInRange(range)
        }
        result shouldBeInstanceOf Either.Success::class
        (result as Either.Success).value shouldBeEqualTo emptyList()
    }

    @Test
    fun retrieveItemsOfAlbumSuccess() = runTest {
        val albumId = 99
        coEvery {
            itemDao.retrieveAllItemsOfAlbum(albumId)
        } returns emptyList()
        val result = localRepository.retrieveItemsOfAlbum(albumId)

        coVerify(exactly = 1) {
            itemDao.retrieveAllItemsOfAlbum(albumId)
        }
        result shouldBeInstanceOf Either.Success::class
        (result as Either.Success).value shouldBeEqualTo emptyList()
    }

    @Test
    fun retrieveAlbumsSuccess() = runTest {
        val listOfItemEntities = listOf(
            ItemEntity().apply {
                albumId = 66
                id = 33
            },
            ItemEntity().apply {
                albumId = 44
                id = 20
            },
            ItemEntity().apply {
                albumId = 88
                id = 40
            },
            ItemEntity().apply {
                albumId = 10
                id = 5
            },
            ItemEntity().apply {
                albumId = 44
                id = 21
            },
            ItemEntity().apply {
                albumId = 88
                id = 41
            }
        )
        val resultMap = listOf(
            AlbumItem(albumId = 66, imagesCount = 1),
            AlbumItem(albumId = 44, imagesCount = 2),
            AlbumItem(albumId = 88, imagesCount = 2),
            AlbumItem(albumId = 10, imagesCount = 1)
        )
        coEvery { itemDao.retrieveAllItems() } returns listOfItemEntities

        val result = localRepository.retrieveAlbums()
        result shouldBeInstanceOf Either.Success::class
        (result as Either.Success).value shouldBeEqualTo resultMap
    }
}
