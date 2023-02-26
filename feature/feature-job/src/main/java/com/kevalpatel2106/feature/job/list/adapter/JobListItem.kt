package com.kevalpatel2106.feature.job.list.adapter

import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.feature.job.list.adapter.JobListItemType.JOB_ITEM

internal sealed class JobListItem(val listItemType: JobListItemType, val compareId: String) {

    data class JobItem(
        val job: Job,
        val triggeredTimeDifference: TimeDifferenceData,
        val executionTimeDifference: TimeDifferenceData,
    ) : JobListItem(JOB_ITEM, job.id.toString())
}
