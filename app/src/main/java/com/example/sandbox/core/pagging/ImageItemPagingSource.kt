package com.example.sandbox.core.pagging

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.ImageItem
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.utils.Either
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class ImageItemPagingSource(private val localRepository: LocalRepository) : ParentPagingSource<ImageItem>() {
    override fun getRefreshKeyForItem(item: ImageItem): Int =
        ensureValidKey(key = item.id - ITEMS_PER_PAGE)

    override suspend fun loadData(intRange: IntRange): Either<SandboxException, List<ImageItem>> {
        val result = localRepository.retrieveItems(intRange)
        return suspendCancellableCoroutine { continuation ->
            result.fold(
                {
                    continuation.resume(result as Either.Failure)
                },
                { data ->
                    continuation.resume(Either.Success(data.map { it.toImageItem() }))
                }
            )
        }
    }
}
