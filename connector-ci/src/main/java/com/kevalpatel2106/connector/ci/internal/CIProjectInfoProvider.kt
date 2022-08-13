package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId

interface CIProjectInfoProvider {
    suspend fun getProjectsUpdatedDesc(
        accountId: AccountId,
        url: Url,
        token: Token,
        cursor: String?,
        limit: Int,
    ): PagedData<Project>
}
