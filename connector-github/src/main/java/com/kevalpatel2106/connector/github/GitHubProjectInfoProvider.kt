package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.entity.PagedData
import com.kevalpatel2106.connector.ci.internal.CIProjectInfoProvider
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint.Companion.FIRST_PAGE_CURSOR
import com.kevalpatel2106.connector.github.network.mapper.ProjectMapper
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import javax.inject.Inject

internal class GitHubProjectInfoProvider @Inject constructor(
    private val retrofitClient: GitHubRetrofitClient,
    private val projectMapper: ProjectMapper
) : CIProjectInfoProvider {

    override suspend fun getProjectsUpdatedDesc(
        accountId: AccountId,
        url: Url,
        token: Token,
        cursor: String?,
        limit: Int,
    ): PagedData<Project> {
        val pageNumber = cursor?.toInt() ?: FIRST_PAGE_CURSOR

        val projectDto = retrofitClient
            .getService(baseUrl = url, token = token)
            .getProjectsUpdatedDesc(page = pageNumber, perPage = limit)

        val nextCursor = if (projectDto.isEmpty()) {
            null
        } else {
            pageNumber + 1
        }
        return PagedData(
            data = projectDto.map { projectMapper(it, accountId) },
            nextCursor = nextCursor?.toString(),
        )
    }
}
