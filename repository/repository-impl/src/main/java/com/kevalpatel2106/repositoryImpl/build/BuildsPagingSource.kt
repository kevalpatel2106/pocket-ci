package com.kevalpatel2106.repositoryImpl.build

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl.Companion.PAGE_SIZE
import timber.log.Timber

internal class BuildsPagingSource(
    private val projectBasic: ProjectBasic,
    private val accountBasic: AccountBasic,
    private val ciConnector: CIConnector,
) : PagingSource<String, Build>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Build> {
        var result: LoadResult<String, Build> = emptyResult()

        runCatching {
            ciConnector.getBuildsByCreatedDesc(
                projectBasic = projectBasic,
                accountBasic = accountBasic,
                cursor = params.key,
                limit = PAGE_SIZE,
            )
        }.onSuccess { response ->
            result = LoadResult.Page(
                data = response.data,
                prevKey = null, // Only paging forward.
                nextKey = if (response.data.isEmpty()) null else response.nextCursor,
            )
        }.onFailure { error ->
            Timber.e(error)
            result = LoadResult.Error(error)
        }
        return result
    }

    override fun getRefreshKey(state: PagingState<String, Build>): String? {
        // No anchoring is supported.
        return STARTING_PAGE_CURSOR
    }

    private fun emptyResult(): LoadResult<String, Build> {
        return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
    }

    companion object {
        private val STARTING_PAGE_CURSOR = null
    }
}
