package com.kevalpatel2106.repository

import androidx.paging.PagingData
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
import kotlinx.coroutines.flow.Flow

interface ProjectRepo {
    suspend fun getProject(remoteId: String, accountId: AccountId): Project?
    fun getProjects(accountId: AccountId): Flow<PagingData<Project>>
}
