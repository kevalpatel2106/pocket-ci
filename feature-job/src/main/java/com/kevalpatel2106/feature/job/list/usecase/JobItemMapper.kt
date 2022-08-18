package com.kevalpatel2106.feature.job.list.usecase

import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.feature.job.list.adapter.JobListItem.JobItem

internal interface JobItemMapper {
    operator fun invoke(job: Job): JobItem
}
