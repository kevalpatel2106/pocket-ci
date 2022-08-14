package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIBuildInfoProvider
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.mapper.BuildMapper
import com.kevalpatel2106.core.extentions.notSupported
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import javax.inject.Inject

internal class GitHubBuildInfoProvider @Inject constructor(
    private val retrofitClient: GitHubRetrofitClient,
    private val buildMapper: BuildMapper,
) : CIBuildInfoProvider {

    override suspend fun getBuildsByCreatedDesc(
        projectId: ProjectId,
        projectName: String,
        projectOwner: String,
        url: Url,
        token: Token,
        cursor: String?,
        limit: Int,
    ): PagedData<Build> {
        val pageNumber = cursor?.toInt() ?: GitHubEndpoint.FIRST_PAGE_CURSOR

        val buildsDto = retrofitClient
            .getService(baseUrl = url, token = token)
            .getBuildsTriggeredAtDesc(projectOwner, projectName, limit, pageNumber)

        val nextCursor = if (buildsDto.workflowRuns.isEmpty()) {
            null
        } else {
            pageNumber + 1
        }
        return PagedData(
            data = buildsDto.workflowRuns.map { buildMapper(projectId, it) },
            nextCursor = nextCursor?.toString(),
        )
    }

    override suspend fun getBuildLogs(
        url: Url,
        token: Token,
        projectId: ProjectId,
        projectName: String,
        projectOwner: String,
        buildId: BuildId,
    ): String {
        notSupported(CIType.GITHUB, "GitHub doesn't support build logs.")
    }
}
