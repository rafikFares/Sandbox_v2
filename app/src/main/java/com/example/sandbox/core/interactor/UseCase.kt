package com.example.sandbox.core.interactor

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.utils.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params?): Either<SandboxException, Type>

    operator fun invoke(
        params: Params?,
        scope: CoroutineScope,
        onResult: (Either<SandboxException, Type>) -> Unit
    ) {
        scope.launch {
            val deferred = async {
                run(params)
            }
            val result = deferred.await()
            withContext(Dispatchers.Main) {
                onResult(result)
            }
        }
    }
}
