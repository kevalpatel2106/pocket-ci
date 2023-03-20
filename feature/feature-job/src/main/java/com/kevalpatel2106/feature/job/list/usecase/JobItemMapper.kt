package com.kevalpatel2106.feature.job.list.usecase

import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.feature.job.list.model.JobListItem.JobItem
import java.util.Date

internal interface JobItemMapper {
    operator fun invoke(job: Job, now: Date = Date()): JobItem
}
