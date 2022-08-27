package com.kevalpatel2106.feature.job.list.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getJobFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class JobListItemTest {
    private val fixture = KFixture()

    @Test
    fun `check job list item type for job item `() {
        val actual = JobListItem.JobItem(getJobFixture(fixture), fixture(), fixture()).listItemType

        assertEquals(JobListItemType.JOB_ITEM, actual)
    }

    @Test
    fun `check compare id for job item is same as job id`() {
        val job = getJobFixture(fixture)

        val actual = JobListItem.JobItem(job, fixture(), fixture()).compareId

        assertEquals(job.id.toString(), actual)
    }
}
