package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId

interface CIJobsProvider {

    suspend fun getJobs(
        accountBasic: AccountBasic,
        projectBasic: ProjectBasic,
        buildId: BuildId,
        cursor: String?,
        limit: Int,
    ): PagedData<Job>

    suspend fun getJobLogs(
        accountBasic: AccountBasic,
        projectBasic: ProjectBasic,
        buildId: BuildId,
        jobId: JobId,
    ): String
}
