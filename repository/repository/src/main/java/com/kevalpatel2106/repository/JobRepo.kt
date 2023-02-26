package com.kevalpatel2106.repository

import androidx.paging.PagingData
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId
import kotlinx.coroutines.flow.Flow

interface JobRepo {
    fun getJobs(accountId: AccountId, projectId: ProjectId, buildId: BuildId): Flow<PagingData<Job>>

    suspend fun getJobLogs(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
        jobId: JobId,
    ): String
}
