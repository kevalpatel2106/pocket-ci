package com.kevalpatel2106.feature.job.list.model

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getJobFixture
import com.kevalpatel2106.feature.job.list.model.JobListItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class JobListItemTest {
    private val fixture = KFixture()

    @Test
    fun `check compare id for job item is same as job id`() {
        val job = getJobFixture(fixture)

        val actual = JobListItem.JobItem(job, fixture(), fixture()).key

        assertEquals(job.id.toString(), actual)
    }
}
