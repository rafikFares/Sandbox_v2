package com.example.sandbox.core.utils

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.exception.SandboxException
import kotlin.test.Test
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf

class EitherTest : BaseUnitTest() {

    @Test
    fun successIsCorrect() {
        val eitherSuccess = Either.Success("tmp")

        eitherSuccess shouldBeInstanceOf Either::class.java
        eitherSuccess.isSuccessful shouldBe true
        eitherSuccess.fold(
            {},
            {
                it shouldBeInstanceOf String::class.java
                it shouldBeEqualTo "tmp"
            }
        )
    }

    @Test
    fun failureIsCorrect() {
        val eitherFailure = Either.Failure(SandboxException.EmptyParamsException)

        eitherFailure shouldBeInstanceOf Either::class.java
        eitherFailure.isSuccessful shouldBe false
        eitherFailure.fold(
            {
                it shouldBeInstanceOf SandboxException::class.java
                it shouldBeEqualTo SandboxException.EmptyParamsException
            },
            {}
        )
    }

    @Test
    fun foldRunSuccessTask() {
        val eitherSuccess = Either.Success("tmp")
        val result = eitherSuccess.fold(
            {
                fail("Error")
            },
            {
                it
            }
        )

        result shouldBeEqualTo "tmp"
    }

    @Suppress("UNREACHABLE_CODE")
    @Test
    fun extensionIfIsSuccessThanCorrect() {
        val eitherFailure = Either.Failure(SandboxException.EmptyParamsException)
        val eitherSuccess = Either.Success("tmp")
        var resultSuccess: String? = null
        var resultFailure: SandboxException? = null

        eitherFailure.ifIsSuccessThan {
            resultFailure = it
        }

        eitherSuccess.ifIsSuccessThan {
            resultSuccess = it
        }

        resultFailure shouldBe null
        resultSuccess shouldBeEqualTo "tmp"
    }

    @Suppress("UNREACHABLE_CODE")
    @Test
    fun extensionIfIsFailureThanCorrect() {
        val failureException = SandboxException.EmptyParamsException
        val eitherFailure = Either.Failure(failureException)
        val eitherSuccess = Either.Success("tmp")
        var resultSuccess: String? = null
        var resultFailure: SandboxException? = null

        eitherFailure.ifIsFailureThan {
            resultFailure = it
        }

        eitherSuccess.ifIsFailureThan {
            resultSuccess = it
        }

        resultFailure shouldBe failureException
        resultSuccess shouldBe null
    }

    @Suppress("UNREACHABLE_CODE")
    @Test
    fun extensionIfIsFailureWithThanCorrect() {
        val failureException = SandboxException.EmptyParamsException
        val eitherFailure = Either.Failure(failureException)
        val eitherSuccess = Either.Success("tmp")
        var resultFailure: SandboxException? = null

        eitherFailure.ifIsFailureWithThan(failureException) {
            resultFailure = it
        }

        eitherSuccess.ifIsFailureWithThan(failureException) {
            fail("unreachable code got reached")
        }

        resultFailure shouldBe failureException
    }
}
