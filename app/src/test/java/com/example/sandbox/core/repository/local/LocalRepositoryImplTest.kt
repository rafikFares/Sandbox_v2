package com.example.sandbox.core.repository.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.dao.upsert
import com.example.sandbox.core.repository.local.db.AppDatabase
import com.example.sandbox.core.repository.local.entity.ItemEntity
import com.example.sandbox.core.utils.Either
import com.example.sandbox.core.utils.empty
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Rule
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [28])
class LocalRepositoryImplTest : BaseAndroidTest() {
    private val unconfinedDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var itemDao: ItemDao

    private lateinit var localRepository: LocalRepository

    @BeforeTest
    fun before() {
        appDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = appDatabase.itemDao()
        localRepository = LocalRepositoryImpl(itemDao, unconfinedDispatcher)
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    private val items by lazy {
        val firstAlbumItem = (0..10).map {
            ItemEntity(88, it, String.empty(), String.empty(), String.empty())
        }
        val secondAlbumItem = (11..17).map {
            ItemEntity(99, it, String.empty(), String.empty(), String.empty())
        }
        firstAlbumItem + secondAlbumItem
    }

    private suspend fun fillDb() {
        localRepository.insertOrIgnoreAll(items)
    }

    @Test
    fun insertItemsSuccess() = runTest {
        val result = localRepository.insertOrIgnoreAll(items)

        result shouldBeInstanceOf Either.Success::class.java
        (result as Either.Success).value shouldBe true
    }

    @Test
    fun retrieveItemsOfAlbumSuccess() = runTest {
        fillDb()

        val result = localRepository.retrieveItemsOfAlbum(88)

        result shouldBeInstanceOf Either.Success::class
        (result as Either.Success).value.size shouldBeEqualTo 11
    }

    @Test
    fun updateItemSuccess() = runTest {
        fillDb()

        val updatedItem = ItemEntity(99, 15, "Not Empty", String.empty(), String.empty())

        val result = localRepository.updateItem(updatedItem)

        result shouldBeInstanceOf Either.Success::class

        val dbItem = localRepository.retrieveItem(updatedItem.id)
        dbItem shouldBeInstanceOf Either.Success::class
        (dbItem as Either.Success).value.thumbnailUrl shouldBeEqualTo "Not Empty"
    }

    @Test
    fun updateItemsSuccess() = runTest {
        fillDb()

        val updatedItems = listOf(
            ItemEntity(88, 66, "Not Empty", String.empty(), String.empty()), // doesn't exist
            ItemEntity(88, 5, "Not Empty", String.empty(), String.empty()),
            ItemEntity(99, 15, String.empty(), "Not Empty", String.empty())
        )

        val result = localRepository.updateItems(updatedItems)

        result shouldBeInstanceOf Either.Success::class

        val dbItemException = localRepository.retrieveItem(updatedItems[0].id)
        dbItemException shouldBeInstanceOf Either.Failure::class
        (dbItemException as Either.Failure).value shouldBeEqualTo SandboxException.ElementNotFoundException(66)

        val dbItem1 = localRepository.retrieveItem(updatedItems[1].id)
        dbItem1 shouldBeInstanceOf Either.Success::class
        (dbItem1 as Either.Success).value.thumbnailUrl shouldBeEqualTo "Not Empty"

        val dbItem2 = localRepository.retrieveItem(updatedItems[2].id)
        dbItem2 shouldBeInstanceOf Either.Success::class
        (dbItem2 as Either.Success).value.title shouldBeEqualTo "Not Empty"
    }

    @Test
    fun upsertItemsSuccess() = runTest(unconfinedDispatcher) {
        fillDb()

        val updatedItems = listOf(
            ItemEntity(88, 66, "Not Empty", String.empty(), String.empty()), // will be inserted
            ItemEntity(88, 5, "Not Empty", String.empty(), String.empty()),
            ItemEntity(99, 15, String.empty(), "Not Empty", String.empty())
        )

        upsert(
            updatedItems,
            { itemDao.insertOrIgnoreAll(updatedItems) },
            { itemDao.updateItems(updatedItems) }
        )

        val dbItem0 = localRepository.retrieveItem(updatedItems[0].id)
        dbItem0 shouldBeInstanceOf Either.Success::class
        (dbItem0 as Either.Success).value.albumId shouldBeEqualTo 88

        val dbItem1 = localRepository.retrieveItem(updatedItems[1].id)
        dbItem1 shouldBeInstanceOf Either.Success::class
        (dbItem1 as Either.Success).value.thumbnailUrl shouldBeEqualTo "Not Empty"

        val dbItem2 = localRepository.retrieveItem(updatedItems[2].id)
        dbItem2 shouldBeInstanceOf Either.Success::class
        (dbItem2 as Either.Success).value.title shouldBeEqualTo "Not Empty"
    }

    @Test
    fun retrieveItemSuccess() = runTest {
        fillDb()
        val dbItem = localRepository.retrieveItem(items[0].id)
        dbItem shouldBeInstanceOf Either.Success::class
        (dbItem as Either.Success).value.albumId shouldBeEqualTo items[0].albumId
    }

    @Test
    fun retrieveItemFailWithElementNotFound() = runTest {
        val dbItemException = localRepository.retrieveItem(222222)
        dbItemException shouldBeInstanceOf Either.Failure::class
        (dbItemException as Either.Failure).value shouldBeEqualTo SandboxException.ElementNotFoundException(222222)
    }

    @Test
    fun clearDbSuccess() = runTest {
        fillDb()

        val dbItem = localRepository.retrieveItem(items[0].id)
        dbItem shouldBeInstanceOf Either.Success::class

        localRepository.clearAll()

        val dbItemException = localRepository.retrieveItem(222222)
        dbItemException shouldBeInstanceOf Either.Failure::class
        (dbItemException as Either.Failure).value shouldBeEqualTo SandboxException.ElementNotFoundException(222222)
    }
}
