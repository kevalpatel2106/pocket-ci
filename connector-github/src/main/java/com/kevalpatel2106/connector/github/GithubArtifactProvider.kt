package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIArtifactProvider
import com.kevalpatel2106.connector.github.extension.executePaginatedApiCall
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.interceptor.AuthHeaderInterceptor.Companion.AUTHENTICATION
import com.kevalpatel2106.connector.github.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilder
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.ArtifactDownloadData
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.ArtifactId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.toUrl
import retrofit2.HttpException
import java.net.HttpURLConnection
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
    ) = executePaginatedApiCall(
        currentCursor = cursor,
        executeApiCall = { currentPage ->
            retrofitClient
                .getGithubService(accountBasic.baseUrl, accountBasic.authToken)
                .getArtifacts(
                    owner = projectBasic.owner,
                    repo = projectBasic.name,
                    buildId = buildId.getValue(),
                    perPage = limit,
                    page = currentPage,
                )
        },
        pagedDataMapper = { responseDto ->
            responseDto.artifacts.map { artifactListItemMapper(it, buildId) }
        },
    )

    override suspend fun getArtifactDownloadUrl(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
        artifactId: ArtifactId,
    ): ArtifactDownloadData {
        var downloadUrl = Url.EMPTY
        runCatching {
            retrofitClient
                .getGithubService(accountBasic.baseUrl, accountBasic.authToken)
                .getArtifactDetail(projectBasic.owner, projectBasic.name, artifactId.getValue())
        }.onFailure { error ->
            val isArtifactDeleted = (error as? HttpException)?.code() == HttpURLConnection.HTTP_GONE
            if (!isArtifactDeleted) throw error
        }.onSuccess { responseDto ->
            downloadUrl = responseDto.downloadUrl.toUrl()
        }
        return ArtifactDownloadData(
            url = downloadUrl,
            headers = mapOf(AUTHENTICATION to tokenHeaderValueBuilder(accountBasic.authToken)),
        )
    }
}
