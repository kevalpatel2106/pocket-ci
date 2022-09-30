package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.usecase.ConvertProjectsWithLastUpdateTime
import com.kevalpatel2106.connector.ci.internal.CIProjectInfoProvider
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Project
import javax.inject.Inject

internal class BitriseProjectInfoProvider @Inject constructor(
    private val retrofitClient: BitriseRetrofitClient,
    private val convertProjectsWithLastUpdateTime: ConvertProjectsWithLastUpdateTime,
) : CIProjectInfoProvider {

    override suspend fun getProjectsUpdatedDesc(
        accountBasic: AccountBasic,
        cursor: String?,
        limit: Int,
    ): PagedData<Project> {
        val response = retrofitClient
            .getBitriseService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
            .getProjectsLastBuildAt(next = cursor, limit = limit)
        requireNotNull(response.data)
        return PagedData(
            data = convertProjectsWithLastUpdateTime(accountBasic.localId, response.data),
            nextCursor = response.paging?.nextCursor,
        )
    }
}
