package com.kevalpatel2106.feature.job.list.model

import com.kevalpatel2106.entity.Job

internal sealed class JobListItem(val key: String) {

    data class JobItem(
        val job: Job,
        val triggeredTime: String,
        val executionTime: String,
    ) : JobListItem(job.id.toString())
}
