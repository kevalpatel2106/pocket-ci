package com.kevalpatel2106.repository

import androidx.paging.PagingData
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import kotlinx.coroutines.flow.Flow

interface BuildRepo {
    fun getBuilds(accountId: AccountId, projectId: ProjectId): Flow<PagingData<Build>>
}
