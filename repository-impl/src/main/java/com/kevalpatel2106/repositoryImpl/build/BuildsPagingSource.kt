package com.kevalpatel2106.repositoryImpl.build

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.entity.toToken
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl.Companion.PAGE_SIZE
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDto
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto
import timber.log.Timber
import javax.inject.Inject

internal class BuildsPagingSource(
    private val projectId: ProjectId,
    private val projectName: String,
    private val projectOwner: String,
    private val baseUrl: Url,
    private val authToken: Token,
    private val ciConnector: CIConnector,
) : PagingSource<String, Build>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Build> {
        var result: LoadResult<String, Build> = emptyResult()

        runCatching {
            ciConnector.getBuildsByCreatedDesc(
                projectId = projectId,
                projectName = projectName,
                projectOwner = projectOwner,
                url = baseUrl,
                token = authToken,
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

    class Factory @Inject constructor() {
        fun create(
            accountDto: AccountDto,
            projectDto: ProjectDto,
            ciConnector: CIConnector,
        ) = BuildsPagingSource(
            projectId = projectDto.remoteId.toProjectId(),
            projectName = projectDto.name,
            projectOwner = projectDto.owner,
            baseUrl = accountDto.baseUrl,
            authToken = accountDto.token.toToken(),
            ciConnector,
        )
    }

    companion object {
        private val STARTING_PAGE_CURSOR = null
    }
}
