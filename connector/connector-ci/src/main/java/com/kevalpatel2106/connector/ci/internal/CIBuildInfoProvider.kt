package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.BuildId

interface CIBuildInfoProvider {

    suspend fun getBuildsByCreatedDesc(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        cursor: String?,
        limit: Int,
    ): PagedData<Build>

    suspend fun getBuildLogs(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
    ): String
}
