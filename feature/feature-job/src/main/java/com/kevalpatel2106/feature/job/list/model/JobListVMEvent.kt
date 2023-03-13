package com.kevalpatel2106.feature.job.list.model

import com.kevalpatel2106.entity.DisplayError
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

    object Close : JobListVMEvent()
    data class ShowErrorLoadingJobs(val displayError: DisplayError) : JobListVMEvent()
}
