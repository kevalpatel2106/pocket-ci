package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Project

interface CIProjectInfoProvider {
    suspend fun getProjectsUpdatedDesc(
        accountBasic: AccountBasic,
        cursor: String?,
        limit: Int,
    ): PagedData<Project>
}
