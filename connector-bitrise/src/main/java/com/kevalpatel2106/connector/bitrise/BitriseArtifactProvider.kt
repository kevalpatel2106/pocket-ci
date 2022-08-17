package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.connector.ci.internal.CIArtifactProvider
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.ArtifactId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.toUrl
import javax.inject.Inject

internal class BitriseArtifactProvider @Inject constructor(
    private val retrofitClient: BitriseRetrofitClient,
    private val artifactListItemMapper: ArtifactListItemMapper,
) : CIArtifactProvider {

    override suspend fun getArtifacts(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
        cursor: String?,
        limit: Int
    ): PagedData<Artifact> {
        val response = retrofitClient
            .getService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
            .getArtifacts(
                appSlug = projectBasic.remoteId.getValue(),
                buildSlug = buildId.getValue(),
                next = cursor,
                limit = limit,
            )
        requireNotNull(response.data)
        return PagedData(
            data = response.data.map { artifactListItemMapper(it, buildId) },
            nextCursor = response.paging?.nextCursor,
        )
    }

    override suspend fun getArtifactDownloadUrl(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
        artifactId: ArtifactId
    ): Url {
        val response = retrofitClient
            .getService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
            .getArtifactDetail(
                appSlug = projectBasic.remoteId.getValue(),
                buildSlug = buildId.getValue(),
                artifactSlug = artifactId.getValue()
            )
        requireNotNull(response.data)
        return response.data.downloadUrl.toUrl()
    }
}
