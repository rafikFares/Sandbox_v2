package com.example.sandbox

import io.mockk.MockKAnnotations
import org.junit.rules.TestRule

interface BaseTest {
    fun create(testClass: Any) = TestRule { statement, _ ->
        MockKAnnotations.init(testClass, relaxUnitFun = true)
        statement
    }
}
