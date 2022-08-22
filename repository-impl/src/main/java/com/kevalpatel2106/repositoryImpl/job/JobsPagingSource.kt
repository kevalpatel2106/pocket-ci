package com.kevalpatel2106.repositoryImpl.job

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.repositoryImpl.job.JobRepoImpl.Companion.PAGE_SIZE
import timber.log.Timber
import javax.inject.Inject

internal class JobsPagingSource(
    private val accountBasic: AccountBasic,
    private val projectBasic: ProjectBasic,
    private val buildId: BuildId,
    private val ciConnector: CIConnector,
) : PagingSource<String, Job>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Job> {
        var result: LoadResult<String, Job> = emptyResult()

        runCatching {
            ciConnector.getJobs(
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

    override fun getRefreshKey(state: PagingState<String, Job>): String? {
        // No anchoring is supported.
        return STARTING_PAGE_CURSOR
    }

    private fun emptyResult(): LoadResult<String, Job> {
        return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
    }

    class Factory @Inject constructor() {

        fun create(
            buildId: BuildId,
            accountBasic: AccountBasic,
            projectBasic: ProjectBasic,
            ciConnector: CIConnector,
        ) = JobsPagingSource(
            projectBasic = projectBasic,
            accountBasic = accountBasic,
            ciConnector = ciConnector,
            buildId = buildId,
        )
    }

    companion object {
        private val STARTING_PAGE_CURSOR = null
    }
}
