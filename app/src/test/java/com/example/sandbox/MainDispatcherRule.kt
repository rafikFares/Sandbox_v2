package com.example.sandbox

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * use :
@get:Rule
val mainDispatcherRule = MainDispatcherRule()
val mainDispatcher: CoroutineContext = mainDispatcherRule.testDispatcher
val anotherTestDispatcher = UnconfinedTestDispatcher(mainDispatcher.scheduler)
@Test
fun injectingTestDispatchers() = runTest { // Uses Main’s scheduler
// Use the UnconfinedTestDispatcher from the Main dispatcher
val unconfinedRepo = Repository(mainDispatcherRule.testDispatcher)
// Create a new StandardTestDispatcher (uses Main’s scheduler)
val standardRepo = Repository(StandardTestDispatcher())
}
 */

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
