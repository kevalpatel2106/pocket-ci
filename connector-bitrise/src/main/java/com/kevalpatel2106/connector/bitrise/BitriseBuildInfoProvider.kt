package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.mapper.BuildMapper
import com.kevalpatel2106.connector.ci.internal.CIBuildInfoProvider
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.BuildId
import javax.inject.Inject

internal class BitriseBuildInfoProvider @Inject constructor(
    private val retrofitClient: BitriseRetrofitClient,
    private val buildMapper: BuildMapper,
) : CIBuildInfoProvider {

    override suspend fun getBuildsByCreatedDesc(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        cursor: String?,
        limit: Int,
    ): PagedData<Build> {
        val response = retrofitClient
            .getService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
            .getBuildsByCreatedAt(
                appSlug = projectBasic.remoteId.getValue(),
                next = cursor,
                limit = limit,
            )
        requireNotNull(response.data)
        return PagedData(
            data = response.data.map { buildMapper(projectBasic.remoteId, it) },
            nextCursor = response.paging?.nextCursor,
        )
    }

    override suspend fun getBuildLogs(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
    ): String {
        val client = retrofitClient.getService(
            baseUrl = accountBasic.baseUrl,
            token = accountBasic.authToken,
        )
        val response = client.getBuildsLogs(
            appSlug = projectBasic.remoteId.getValue(),
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
