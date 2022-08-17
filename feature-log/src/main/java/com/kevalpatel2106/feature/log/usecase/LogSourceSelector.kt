package com.kevalpatel2106.feature.log.usecase

import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId

internal interface LogSourceSelector {

    suspend operator fun invoke(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
        jobId: JobId?,
    ): String
}
