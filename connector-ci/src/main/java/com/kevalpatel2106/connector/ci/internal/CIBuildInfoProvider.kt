package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.connector.ci.entity.PagedData
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.ProjectId

interface CIBuildInfoProvider {

    @Suppress("LongParameterList")
    suspend fun getBuildsByCreatedDesc(
        projectId: ProjectId,
        projectName: String,
        projectOwner: String,
        url: Url,
        token: Token,
        cursor: String?,
        limit: Int,
    ): PagedData<Build>
}
