package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIBuildInfoProvider
import com.kevalpatel2106.connector.github.extension.executePaginatedApiCall
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.mapper.BuildMapper
import com.kevalpatel2106.core.extentions.notSupported
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.BuildId
import javax.inject.Inject

internal class GitHubBuildInfoProvider @Inject constructor(
    private val retrofitClient: GitHubRetrofitClient,
    private val buildMapper: BuildMapper,
) : CIBuildInfoProvider {

    override suspend fun getBuildsByCreatedDesc(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        cursor: String?,
        limit: Int,
    ) = executePaginatedApiCall(
        currentCursor = cursor,
        executeApiCall = { currentPage ->
            retrofitClient
                .getGithubService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
                .getBuildsTriggeredAtDesc(
                    owner = projectBasic.owner,
                    repo = projectBasic.name,
                    perPage = limit,
                    page = currentPage,
                )
        },
        pagedDataMapper = { buildsDto ->
            buildsDto.workflowRuns.map { buildMapper(projectBasic.remoteId, it) }
        },
    )

    override suspend fun getBuildLogs(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
    ): String {
        notSupported(CIType.GITHUB, "GitHub doesn't support build logs.")
    }
}
