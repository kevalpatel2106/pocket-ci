package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId

@Suppress("LongParameterList")
interface CIArtifactProvider {

    suspend fun getArtifacts(
        projectId: ProjectId,
        projectName: String,
        projectOwner: String,
        buildId: BuildId,
        url: Url,
        token: Token,
        cursor: String?,
        limit: Int,
    ): PagedData<Artifact>
}
