package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIArtifactProvider
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import javax.inject.Inject

internal class GithubArtifactProvider @Inject constructor(
    private val retrofitClient: GitHubRetrofitClient,
    private val artifactListItemMapper: ArtifactListItemMapper
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
        val pageNumber = cursor?.toInt() ?: GitHubEndpoint.FIRST_PAGE_CURSOR

        val responseDto = retrofitClient
            .getService(baseUrl = url, token = token)
            .getArtifacts(projectOwner, projectName, buildId.getValue(), limit, pageNumber)

        val nextCursor = if (responseDto.artifacts.isEmpty()) {
            null
        } else {
            pageNumber + 1
        }
        return PagedData(
            data = responseDto.artifacts.map { artifactListItemMapper(it, buildId) },
            nextCursor = nextCursor?.toString(),
        )
    }
}
