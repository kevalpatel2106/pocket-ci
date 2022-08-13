package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId

@Suppress("LongParameterList")
interface CIJobsProvider {

    suspend fun getJobs(
        url: Url,
        token: Token,
        projectId: ProjectId,
        projectName: String,
        projectOwner: String,
        buildId: BuildId,
        cursor: String?,
        limit: Int,
    ): PagedData<Job>

    suspend fun getJobLogs(
        url: Url,
        token: Token,
        projectId: ProjectId,
        projectName: String,
        projectOwner: String,
        buildId: BuildId,
        jobId: JobId,
    ): String
}
