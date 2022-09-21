package com.kevalpatel2106.repository

import androidx.paging.PagingData
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import kotlinx.coroutines.flow.Flow

interface ProjectRepo {
    suspend fun getProject(remoteId: ProjectId, accountId: AccountId): Project?
    fun getProjects(accountId: AccountId): Flow<PagingData<Project>>
    suspend fun pinProject(remoteId: ProjectId, accountId: AccountId)
    suspend fun unpinProject(remoteId: ProjectId, accountId: AccountId)
}
