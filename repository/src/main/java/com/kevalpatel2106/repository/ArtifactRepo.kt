package com.kevalpatel2106.repository

import androidx.paging.PagingData
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ArtifactId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import kotlinx.coroutines.flow.Flow

interface ArtifactRepo {

    fun getArtifacts(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
    ): Flow<PagingData<Artifact>>

    suspend fun getArtifactDownloadUrl(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
        artifactId: ArtifactId
    ): Url
}
