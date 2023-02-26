package com.kevalpatel2106.feature.job.list.usecase

import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.feature.job.list.adapter.JobListItem.JobItem
import javax.inject.Inject

internal class JobItemMapperImpl @Inject constructor() : JobItemMapper {

    override fun invoke(job: Job) = JobItem(
        job = job,
        triggeredTimeDifference = TimeDifferenceData(
            dateOfEventStart = job.triggeredAt,
            dateOfEventEnd = null,
        ),
        executionTimeDifference = TimeDifferenceData(
            dateOfEventStart = job.triggeredAt,
            dateOfEventEnd = job.finishedAt,
        ),
    )
}
