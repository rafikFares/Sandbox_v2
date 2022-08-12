package com.example.sandbox.core.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.utils.Either
import kotlin.math.max

abstract class DefaultPagingSource<T : Any> : PagingSource<Int, T>() {

    abstract suspend fun loadData(intRange: IntRange): Either<SandboxException, List<T>>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(ITEMS_PER_PAGE)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(ITEMS_PER_PAGE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        // Start paging with the STARTING_KEY if this is the first load
        val start = params.key ?: STARTING_KEY

        // Load as many items as hinted by params.loadSize
        val range = IntRange(start, start + ITEMS_PER_PAGE)

        val result = loadData(range)

        return when (result) {
            is Either.Failure -> {
                LoadResult.Error(result.value)
            }
            is Either.Success -> {
                // Make sure we don't try to load items behind the STARTING_KEY
                val prevKey = if (start > 0) ensureValidKey(start - result.value.size) else null

                val nextKey = if (result.value.isNotEmpty()) start + result.value.size else null

                val data = result.value

                LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        }
    }

    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    protected fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    companion object {
        private const val STARTING_KEY = 0
        const val ITEMS_PER_PAGE = 50
    }
}
