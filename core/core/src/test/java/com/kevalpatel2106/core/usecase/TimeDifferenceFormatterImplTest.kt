package com.kevalpatel2106.core.usecase

import android.app.Application
import android.content.res.Resources
import androidx.annotation.StringRes
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.plus
import com.kevalpatel2106.coreTest.toDate
import com.kevalpatel2106.core.resources.R
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.Calendar
import java.util.concurrent.TimeUnit

internal class TimeDifferenceFormatterImplTest {
    private val testString = "short"

    private val res = mock<Resources> {
        on { getString(any(), any(), any()) } doReturn testString
        on { getString(any(), any(), any(), any()) } doReturn testString
        on { getQuantityString(any(), any(), any()) } doReturn testString
        on { getQuantityString(any(), any(), any(), any()) } doReturn testString
    }
    private val application = mock<Application> {
        on { resources } doReturn res
    }
    private val subject = TimeDifferenceFormatterImpl(application)
    private val testAppendText = fixture<String>()

    @ParameterizedTest(
        name = "given difference between start and end date is {0} seconds when invoke less precise then check expected time difference unit is {2} and string resource used is {3}"
    )
    @MethodSource("provideValuesForLessPreciseTest")
    fun testLessPrecise(
        differenceSeconds: Long,
        appendText: String?,
        expectedUnit: Int,
        @StringRes expectedResource: Int,
    ) {
        val dateStart = Calendar.getInstance()
        val dateEnd = dateStart.plus(differenceSeconds.toInt(), Calendar.SECOND)

        subject(dateStart.toDate(), dateEnd.toDate(), appendText, false)

        verify(res).getQuantityString(
            eq(expectedResource),
            eq(expectedUnit),
            eq(expectedUnit),
            eq(appendText),
        )
    }

    @Test
    fun `given start time is after end time when invoked then check exception`() {
        val dateEnd = Calendar.getInstance()
        val dateStart = dateEnd.plus(1, Calendar.SECOND)

        assertThrowsExactly(IllegalArgumentException::class.java) {
            subject(dateStart.toDate(), dateEnd.toDate(), fixture(), true)
        }
    }

    @Test
    fun `given difference second is 1 when invoked more precise time then check time unit and resource used`() {
        val dateStart = Calendar.getInstance()
        val dateEnd = dateStart.plus(1, Calendar.SECOND)

        subject(dateStart.toDate(), dateEnd.toDate(), testAppendText, true)

        verify(res).getString(R.string.time_format_second_short, 1, testAppendText)
    }

    @Test
    fun `given difference is 1 minute 1 second  when invoked more precise time then check time unit and resource used`() {
        val dateStart = Calendar.getInstance()
        val dateEnd = dateStart.plus(
            (1 * MINUTE + 1 * SECOND).toInt(),
            Calendar.SECOND,
        )

        subject(dateStart.toDate(), dateEnd.toDate(), testAppendText, true)

        verify(res).getString(R.string.time_format_minute_second, 1, 1, testAppendText)
    }

    @Test
    fun `given difference is 1 hour 1 minute 1 second  when invoked more precise time then check time unit and resource used`() {
        val dateStart = Calendar.getInstance()
        val dateEnd = dateStart.plus(
            (1 * HOUR + 1 * MINUTE + 1 * SECOND).toInt(),
            Calendar.SECOND,
        )

        subject(dateStart.toDate(), dateEnd.toDate(), testAppendText, true)

        verify(res).getString(R.string.time_format_hour_minute, 1, 1, testAppendText)
    }

    @Test
    fun `given difference 1 day 1 hour 1 minute 1 second when invoked more precise time then check time unit and resource used`() {
        val dateStart = Calendar.getInstance()
        val dateEnd = dateStart.plus(
            (1 * DAY + 1 * HOUR + 1 * MINUTE + 1 * SECOND).toInt(),
            Calendar.SECOND,
        )

        subject(dateStart.toDate(), dateEnd.toDate(), testAppendText, true)

        verify(res).getString(R.string.time_format_day_hour, 1, 1, testAppendText)
    }

    @Test
    fun `given difference 1 week 1 day 1 hour 1 minute 1 second when invoked more precise time then check time unit and resource used`() {
        val dateStart = Calendar.getInstance()
        val dateEnd = dateStart.plus(
            (1 * WEEK + 1 * DAY + 1 * HOUR + 1 * MINUTE + 1 * SECOND).toInt(),
            Calendar.SECOND,
        )

        subject(dateStart.toDate(), dateEnd.toDate(), testAppendText, true)

        verify(res).getQuantityString(R.plurals.time_format_week, 1, 1, testAppendText)
    }

    companion object {
        private val fixture = KFixture()
        private val testAppendText = fixture<String>()
        private val SECOND = TimeUnit.SECONDS.convert(1, TimeUnit.SECONDS)
        private val MINUTE = TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
        private val HOUR = TimeUnit.SECONDS.convert(1, TimeUnit.HOURS)
        private val DAY = TimeUnit.SECONDS.convert(1, TimeUnit.DAYS)
        private val WEEK = TimeUnit.SECONDS.convert(7, TimeUnit.DAYS)
        private val YEAR = TimeUnit.SECONDS.convert(365, TimeUnit.DAYS)

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForLessPreciseTest() = listOf(
            // Format difference, append text, expected unit, expected resource
            arguments(SECOND, testAppendText, 1, R.plurals.time_format_second),
            arguments((2.52 * SECOND).toLong(), testAppendText, 2, R.plurals.time_format_second),
            arguments(MINUTE, testAppendText, 1, R.plurals.time_format_minute),
            arguments((2.51 * MINUTE).toLong(), testAppendText, 2, R.plurals.time_format_minute),
            arguments(HOUR, testAppendText, 1, R.plurals.time_format_hour),
            arguments((2.5 * HOUR).toLong(), testAppendText, 2, R.plurals.time_format_hour),
            arguments(DAY, testAppendText, 1, R.plurals.time_format_day),
            arguments((2.1 * DAY).toLong(), testAppendText, 2, R.plurals.time_format_day),
            arguments(WEEK, testAppendText, 1, R.plurals.time_format_week),
            arguments((2.99 * WEEK).toLong(), testAppendText, 2, R.plurals.time_format_week),
            arguments(YEAR, testAppendText, 1, R.plurals.time_format_year),
            arguments((2.4 * YEAR).toLong(), testAppendText, 2, R.plurals.time_format_year),
        )
    }
}
