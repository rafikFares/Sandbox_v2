package com.example.sandbox.core.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import java.io.File
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@ExperimentalCoroutinesApi
class PreferenceRepositoryImplTest : BaseAndroidTest() {

    private val unconfinedDispatcher = UnconfinedTestDispatcher()
    private val testCoroutineScope = TestScope(unconfinedDispatcher)
    private lateinit var testDataStore: DataStore<Preferences>

    private lateinit var preferenceRepository: PreferenceRepository

    @Before
    fun setup() {
        testDataStore = PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile = { appContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
        )
        preferenceRepository = PreferenceRepositoryImpl(testDataStore, unconfinedDispatcher)
    }

    @After
    fun removeDatastore() {
        File(
            appContext.filesDir,
            "datastore"
        ).deleteRecursively()

        testCoroutineScope.cancel()
    }

    @Test
    fun testSaveGetStringSuccess() = testCoroutineScope.runTest {
        val mainValue = "UserName"
        preferenceRepository.save(PreferenceKey.UserName, mainValue)
        val result = preferenceRepository.get(PreferenceKey.UserName, "defaultValue")
        result shouldBeEqualTo mainValue
    }

    @Test
    fun testSaveGetLongSuccess() = testCoroutineScope.runTest {
        val mainValue = 111L
        preferenceRepository.save(PreferenceKey.LastFetch, mainValue)
        val result = preferenceRepository.get(PreferenceKey.LastFetch, 666)
        result shouldBeEqualTo mainValue
    }

    @Test
    fun testGetDefaultValue() = testCoroutineScope.runTest {
        val defaultValue = "defaultValue"
        val result = preferenceRepository.get(PreferenceKey.UserToken, defaultValue)
        result shouldBeEqualTo defaultValue
    }

    @Test(expected = SandboxException.PreferenceKeyException::class)
    fun testSaveWrongKeyException() = testCoroutineScope.runTest {
        val mainValue = "LastFetch"
        preferenceRepository.save(PreferenceKey.LastFetch, mainValue)
    }

    @Test
    fun testDelete() = testCoroutineScope.runTest {
        val mainValue = "UserName"
        val defaultValue = "defaultValue"

        preferenceRepository.save(PreferenceKey.UserName, mainValue)
        val resultDeleteSuccess = preferenceRepository.delete(PreferenceKey.UserName)
        Assert.assertTrue(resultDeleteSuccess)

        val resultFetch = preferenceRepository.get(PreferenceKey.UserName, defaultValue)
        resultFetch shouldBeEqualTo defaultValue

        val resultDeleteFail = preferenceRepository.delete(PreferenceKey.UserName)
        Assert.assertFalse(resultDeleteFail)
    }
}
