package com.example.sandbox.core.interactor

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.utils.Either
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf

class UseCaseTest : BaseUnitTest() {

    private class TestClass : UseCase<String, String>() {
        override suspend fun run(params: String?): Either<SandboxException, String> {
            return Either.Success("$params + run")
        }
    }

    @Test
    fun runReturnSuccess() {
        val testClass = TestClass()
        val result = runBlocking {
            testClass.run("params")
        }

        result shouldBeInstanceOf Either.Success::class.java
        result shouldBeEqualTo Either.Success("params + run")
    }
}
