package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIProjectInfoProvider
import com.kevalpatel2106.connector.github.extension.executePaginatedApiCall
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.mapper.ProjectMapper
import com.kevalpatel2106.entity.AccountBasic
import javax.inject.Inject

internal class GitHubProjectInfoProvider @Inject constructor(
    private val retrofitClient: GitHubRetrofitClient,
    private val projectMapper: ProjectMapper,
) : CIProjectInfoProvider {

    override suspend fun getProjectsUpdatedDesc(
        accountBasic: AccountBasic,
        cursor: String?,
        limit: Int,
    ) = executePaginatedApiCall(
        currentCursor = cursor,
        executeApiCall = { currentPage ->
            retrofitClient
                .getGithubService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
                .getProjectsUpdatedDesc(page = currentPage, perPage = limit)
        },
        pagedDataMapper = { projectDto ->
            projectDto.map { projectMapper(it, accountBasic.localId) }
        },
    )
}
