package com.kevalpatel2106.repositoryImpl.artifact

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl.Companion.PAGE_SIZE
import timber.log.Timber
import javax.inject.Inject

internal class ArtifactsPagingSource(
    private val projectBasic: ProjectBasic,
    private val accountBasic: AccountBasic,
    private val buildId: BuildId,
    private val ciConnector: CIConnector,
) : PagingSource<String, Artifact>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Artifact> {
        var result: LoadResult<String, Artifact> = emptyResult()

        runCatching {
            ciConnector.getArtifacts(
                projectBasic = projectBasic,
                accountBasic = accountBasic,
                buildId = buildId,
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

    override fun getRefreshKey(state: PagingState<String, Artifact>): String? {
        // No anchoring is supported.
        return STARTING_PAGE_CURSOR
    }

    private fun emptyResult(): LoadResult<String, Artifact> {
        return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
    }

    class Factory @Inject constructor() {

        fun create(
            accountBasic: AccountBasic,
            projectBasic: ProjectBasic,
            buildId: BuildId,
            ciConnector: CIConnector,
        ) = ArtifactsPagingSource(
            projectBasic = projectBasic,
            accountBasic = accountBasic,
            buildId = buildId,
            ciConnector = ciConnector,
        )
    }

    companion object {
        private val STARTING_PAGE_CURSOR = null
    }
}
