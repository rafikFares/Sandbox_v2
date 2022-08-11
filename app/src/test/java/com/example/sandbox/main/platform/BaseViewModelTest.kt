package com.example.sandbox.main.platform

import androidx.lifecycle.LiveData
import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.core.exception.SandboxException
import kotlin.test.Test
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf


class BaseViewModelTest : BaseAndroidTest() {

    private class TestViewModel : BaseViewModel() {
        override val uiState: StateFlow<BaseUiState?>
            get() = MutableStateFlow(null)

        override fun log(message: String, exception: Exception?) {
            // do nothing
        }

        fun fail(exception: SandboxException) {
            super.handleFailure(exception)
        }
    }


    @Test
    fun updateFailureLiveDataAfterHandlingFailure() {
        val viewModel = TestViewModel()
        val exception = SandboxException.EmptyParamsException

        viewModel.fail(exception)

        val failure = viewModel.failure
        val error = viewModel.failure.value

        failure shouldBeInstanceOf LiveData::class.java
        error shouldBe exception
    }
}
