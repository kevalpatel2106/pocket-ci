package com.kevalpatel2106.feature.jobs.list.adapter

import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.feature.jobs.list.adapter.JobListItemType.JOB_ITEM

internal sealed class JobListItem(val listItemType: JobListItemType, val compareId: String) {

    data class JobItem(
        val job: Job,
        val triggeredTimeDifference: TimeDifferenceData,
        val executionTimeDifference: TimeDifferenceData,
    ) : JobListItem(JOB_ITEM, job.id.toString()) {

        constructor(job: Job) : this(
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
}
