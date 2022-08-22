package com.kevalpatel2106.feature.job

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getJobFixture
import com.kevalpatel2106.feature.job.list.adapter.JobListItem

internal fun getJobItemFixture(fixture: KFixture) = JobListItem.JobItem(
    job = getJobFixture(fixture),
    triggeredTimeDifference = fixture(),
    executionTimeDifference = fixture(),
)
