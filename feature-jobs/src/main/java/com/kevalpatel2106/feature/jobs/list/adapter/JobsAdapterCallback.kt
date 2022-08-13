package com.kevalpatel2106.feature.jobs.list.adapter

import com.kevalpatel2106.entity.Job

internal interface JobsAdapterCallback {
    fun onJobSelected(job: Job)
}
