package com.kevalpatel2106.feature.job.list.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.usecase.TimeDifferenceFormatter
import com.kevalpatel2106.coreTest.getJobFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.atLeast
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.Date

internal class JobItemMapperImplTest {
    private val fixture = KFixture()
    private val timeDifferenceFormatter = mock<TimeDifferenceFormatter> {
        on { invoke(any(), any(), anyOrNull(), eq(false)) } doReturn TIME_DIFFERENCE_TEXT
        on { invoke(any(), any(), anyOrNull(), eq(true)) } doReturn PRECISE_TIME_DIFFERENCE_TEXT
    }
    private val subject = JobItemMapperImpl(timeDifferenceFormatter)

    @Test
    fun `given job when mapped then check triggered time difference`() {
        val job = getJobFixture(fixture)

        val actual = subject(job).triggeredTime

        assertEquals(TIME_DIFFERENCE_TEXT, actual)
    }

    @Test
    fun `given job when mapped then check execution time difference`() {
        val job = getJobFixture(fixture)

        val actual = subject(job).executionTime

        assertEquals(PRECISE_TIME_DIFFERENCE_TEXT, actual)
    }

    companion object {
        private const val TIME_DIFFERENCE_TEXT = "time_difference_text"
        private const val PRECISE_TIME_DIFFERENCE_TEXT = "precise_time_difference_text"
    }
}
