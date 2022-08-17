package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIArtifactProvider
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.interceptor.AuthHeaderInterceptor.Companion.AUTHENTICATION
import com.kevalpatel2106.connector.github.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilder
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.ArtifactDownloadData
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.ArtifactId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.toUrl
import javax.inject.Inject

internal class GithubArtifactProvider @Inject constructor(
    private val retrofitClient: GitHubRetrofitClient,
    private val artifactListItemMapper: ArtifactListItemMapper,
    private val tokenHeaderValueBuilder: TokenHeaderValueBuilder,
) : CIArtifactProvider {

    override suspend fun getArtifacts(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
        cursor: String?,
        limit: Int,
    ): PagedData<Artifact> {
        val pageNumber = cursor?.toInt() ?: GitHubEndpoint.FIRST_PAGE_CURSOR

        val responseDto = retrofitClient
            .getService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
            .getArtifacts(
                owner = projectBasic.owner,
                repo = projectBasic.name,
                buildId = buildId.getValue(),
                perPage = limit,
                page = pageNumber,
            )

        val nextCursor = if (responseDto.artifacts.isEmpty()) null else pageNumber + 1
        return PagedData(
            data = responseDto.artifacts.map { artifactListItemMapper(it, buildId) },
            nextCursor = nextCursor?.toString(),
        )
    }

    override suspend fun getArtifactDownloadUrl(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
        artifactId: ArtifactId,
    ): ArtifactDownloadData {
        val responseDto = retrofitClient
            .getService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
            .getArtifactDetail(projectBasic.owner, projectBasic.name, artifactId.getValue())
        return ArtifactDownloadData(
            url = responseDto.downloadUrl.toUrl(),
            headers = mapOf(AUTHENTICATION to tokenHeaderValueBuilder(accountBasic.authToken)),
        )
    }
}
