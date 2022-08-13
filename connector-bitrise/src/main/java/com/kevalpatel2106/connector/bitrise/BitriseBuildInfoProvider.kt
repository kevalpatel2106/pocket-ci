package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.mapper.BuildMapper
import com.kevalpatel2106.connector.ci.internal.CIBuildInfoProvider
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import javax.inject.Inject

internal class BitriseBuildInfoProvider @Inject constructor(
    private val retrofitClient: BitriseRetrofitClient,
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
        val response = retrofitClient
            .getService(baseUrl = url, token = token)
            .getBuildsByCreatedAt(appSlug = projectId.getValue(), next = cursor, limit = limit)
        requireNotNull(response.data)
        return PagedData(
            data = response.data.map { buildMapper(projectId, it) },
            nextCursor = response.paging?.nextCursor,
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
        val client = retrofitClient.getService(baseUrl = url, token = token)
        val response = client.getBuildsLogs(
            appSlug = projectId.getValue(),
            buildSlug = buildId.getValue(),
        )
        return if (!response.isArchived) {
            ""
        } else {
            requireNotNull(response.rawLogUrl) {
                "For builds not in progress, raw log url should not be null"
            }
            client.getRawBuildsLogs(response.rawLogUrl)
        }
    }
}
