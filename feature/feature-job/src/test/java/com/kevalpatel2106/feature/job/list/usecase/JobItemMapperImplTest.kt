package com.kevalpatel2106.feature.job.list.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getJobFixture
import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class JobItemMapperImplTest {
    private val fixture = KFixture()
    private val subject = JobItemMapperImpl()

    @Test
    fun `given job when mapped then check triggered time difference`() {
        val job = getJobFixture(fixture)

        val actual = subject(job).triggeredTimeDifference

        val expected = TimeDifferenceData(
            dateOfEventStart = job.triggeredAt,
            dateOfEventEnd = null,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `given job when mapped then check execution time difference`() {
        val job = getJobFixture(fixture)

        val actual = subject(job).executionTimeDifference

        val expected = TimeDifferenceData(
            dateOfEventStart = job.triggeredAt,
            dateOfEventEnd = job.finishedAt,
        )
        assertEquals(expected, actual)
    }
}
