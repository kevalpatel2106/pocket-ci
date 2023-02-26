package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.ArtifactDownloadData
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.ArtifactId
import com.kevalpatel2106.entity.id.BuildId

interface CIArtifactProvider {

    suspend fun getArtifacts(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
        cursor: String?,
        limit: Int,
    ): PagedData<Artifact>

    suspend fun getArtifactDownloadUrl(
        projectBasic: ProjectBasic,
        accountBasic: AccountBasic,
        buildId: BuildId,
        artifactId: ArtifactId,
    ): ArtifactDownloadData
}
