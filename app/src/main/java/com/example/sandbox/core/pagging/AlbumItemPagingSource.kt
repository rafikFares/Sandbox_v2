package com.example.sandbox.core.pagging

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.AlbumItem
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.utils.Either
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class AlbumItemPagingSource(private val localRepository: LocalRepository) : ParentPagingSource<AlbumItem>() {

    override fun getRefreshKeyForItem(item: AlbumItem): Int =
        ensureValidKey(key = item.albumId - ITEMS_PER_PAGE)

    override suspend fun loadData(intRange: IntRange): Either<SandboxException, List<AlbumItem>> {
        val result = localRepository.retrieveAlbums(intRange)
        return suspendCancellableCoroutine { continuation ->
            result.fold(
                {
                    continuation.resume(result as Either.Failure)
                },
                { data ->
                    continuation.resume(Either.Success(data.map { AlbumItem(it.key, it.value) }))
                }
            )
        }
    }
}
