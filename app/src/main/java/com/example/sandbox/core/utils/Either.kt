package com.example.sandbox.core.utils


sealed interface Either<out F, out S> {
    data class Success<out S>(val value: S) : Either<Nothing, S>
    data class Failure<out F>(val value: F) : Either<F, Nothing>

    val isSuccessful
        get() = this is Success

    /**
     * Applies failureTask if this is a Failure or successTask if this is a Success.
     */
    fun fold(failureTask: (F) -> Any, successTask: (S) -> Any): Any =
        when (this) {
            is Failure -> failureTask(value)
            is Success -> successTask(value)
        }
}

inline fun <F, S> Either<F, S>.ifIsSuccessThan(function: (data: S) -> Unit): Either<F, S> {
    (this as? Either.Success)?.let {
        function.invoke(it.value)
    }
    return this
}

inline fun <F, S> Either<F, S>.ifIsFailureThan(function: (data: F) -> Unit): Either<F, S> {
    (this as? Either.Failure)?.let {
        function.invoke(it.value)
    }
    return this
}

inline fun <F, S> Either<F, S>.ifIsFailureWithThan(with: F, function: (data: F) -> Unit): Either<F, S> {
    ifIsFailureThan {
        if (it == with) function.invoke(it)
    }
    return this
}
