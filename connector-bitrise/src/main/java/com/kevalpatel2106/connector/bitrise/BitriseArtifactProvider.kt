package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.connector.ci.internal.CIArtifactProvider
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import javax.inject.Inject

internal class BitriseArtifactProvider @Inject constructor(
    private val retrofitClient: BitriseRetrofitClient,
    private val artifactListItemMapper: ArtifactListItemMapper,
) : CIArtifactProvider {

    override suspend fun getArtifacts(
        projectId: ProjectId,
        projectName: String,
        projectOwner: String,
        buildId: BuildId,
        url: Url,
        token: Token,
        cursor: String?,
        limit: Int,
    ): PagedData<Artifact> {
        val response = retrofitClient
            .getService(baseUrl = url, token = token)
            .getArtifacts(
                appSlug = projectId.getValue(),
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
}
