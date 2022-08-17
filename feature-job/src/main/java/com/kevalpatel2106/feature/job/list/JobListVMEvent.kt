package com.kevalpatel2106.feature.job.list

import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId

internal sealed class JobListVMEvent {
    data class OpenLogs(
        val accountId: AccountId,
        val projectId: ProjectId,
        val buildId: BuildId,
        val jobId: JobId,
    ) : JobListVMEvent()

    object RefreshJobs : JobListVMEvent()
    object Close : JobListVMEvent()
    object RetryLoading : JobListVMEvent()
}
