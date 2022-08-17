package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.ci.internal.CIJobsProvider
import com.kevalpatel2106.core.extentions.notSupported
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import javax.inject.Inject

internal class BitriseJobsProvider @Inject constructor() : CIJobsProvider {

    override suspend fun getJobs(
        accountBasic: AccountBasic,
        projectBasic: ProjectBasic,
        buildId: BuildId,
        cursor: String?,
        limit: Int
    ): PagedData<Job> {
        notSupported(CIType.BITRISE, "Bitrise doesn't support Jobs.")
    }

    override suspend fun getJobLogs(
        accountBasic: AccountBasic,
        projectBasic: ProjectBasic,
        buildId: BuildId,
        jobId: JobId
    ): String {
        notSupported(CIType.BITRISE, "Bitrise doesn't support Jobs.")
    }
}
