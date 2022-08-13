package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.usecase.ConvertProjectsWithLastUpdateTime
import com.kevalpatel2106.connector.ci.internal.CIProjectInfoProvider
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import javax.inject.Inject

internal class BitriseProjectInfoProvider @Inject constructor(
    private val retrofitClient: BitriseRetrofitClient,
    private val convertProjectsWithLastUpdateTime: ConvertProjectsWithLastUpdateTime,
) : CIProjectInfoProvider {

    override suspend fun getProjectsUpdatedDesc(
        accountId: AccountId,
        url: Url,
        token: Token,
        cursor: String?,
        limit: Int,
    ): PagedData<Project> {
        val response = retrofitClient
            .getService(baseUrl = url, token = token)
            .getProjectsLastBuildAt(next = cursor, limit = limit)
        requireNotNull(response.data)
        return PagedData(
            data = convertProjectsWithLastUpdateTime(accountId, response.data),
            nextCursor = response.paging?.nextCursor,
        )
    }
}
