package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.coreTest.toDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.text.ParseException
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

internal class IsoDateMapperImplTest {
    private val subject = IsoDateMapperImpl()

    @ParameterizedTest(name = "given date string {0} when mapper invoked then check result date is {1}")
    @MethodSource("provideValues")
    fun `given date sting when mapper invoked then check result date`(
        dateString: String?,
        expectedDate: Date?,
    ) {
        assertEquals(expectedDate, subject(dateString))
    }

    @Test
    fun `given invalid date sting when mapper invoked then check parsing exception thrown`() {
        assertThrows<ParseException> { subject("invalid-format") }
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(null, null),
            arguments("", null),
            arguments("  ", null),
            arguments(
                "2022-06-05T13:01:11Z",
                Calendar.getInstance().apply {
                    set(Calendar.YEAR, 2022)
                    set(Calendar.MONTH, 5)
                    set(Calendar.DAY_OF_MONTH, 5)
                    set(Calendar.HOUR_OF_DAY, 13)
                    set(Calendar.MINUTE, 1)
                    set(Calendar.SECOND, 11)
                    set(Calendar.MILLISECOND, 0)
                    timeZone = TimeZone.getTimeZone("UTC")
                }.toDate(),
            ),
            arguments(
                "2022-06-05T00:00:00Z",
                Calendar.getInstance().apply {
                    set(Calendar.YEAR, 2022)
                    set(Calendar.MONTH, 5)
                    set(Calendar.DAY_OF_MONTH, 5)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    timeZone = TimeZone.getTimeZone("UTC")
                }.toDate(),
            ),
        )
    }
}
