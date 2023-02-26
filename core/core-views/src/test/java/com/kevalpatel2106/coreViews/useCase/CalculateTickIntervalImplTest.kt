package com.kevalpatel2106.coreViews.useCase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.minus
import com.kevalpatel2106.coreTest.toDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

internal class CalculateTickIntervalImplTest {
    private val fixture = KFixture()
    private val subject = CalculateTickIntervalImpl()

    @ParameterizedTest(
        name = "given past date {0} and showing precise time {1} when invoke then verify expected tick interval is {3}"
    )
    @MethodSource("provideValues")
    fun `given past date and showing precise time when invoke then verify expected tick interval`(
        now: Date,
        shoeMorePrecise: Boolean,
        pastDate: Date,
        expectedIntervalMills: Long,
    ) {
        val actualInterval = subject(pastDate, shoeMorePrecise, now)

        assertEquals(expectedIntervalMills, actualInterval)
    }

    @Test
    fun `given past date is in the future when invoke then error thrown`() {
        val futureDate = Date(now.timeInMillis + 60_000)

        assertThrowsExactly(IllegalArgumentException::class.java) {
            subject(futureDate, fixture(), now.toDate())
        }
    }

    companion object {
        private val now = Calendar.getInstance()

        @Suppress("unused", "LongMethod")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: now time, show more precise, past date, expected mills
            // Less precise
            arguments(
                now.toDate(),
                false,
                now.minus(900, Calendar.MILLISECOND).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS),
            ),
            arguments(
                now.toDate(),
                false,
                now.minus(59, Calendar.SECOND).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS),
            ),
            arguments(
                now.toDate(),
                false,
                now.minus(59, Calendar.MINUTE).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES),
            ),
            arguments(
                now.toDate(),
                false,
                now.minus(1, Calendar.HOUR).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS),
            ),
            arguments(
                now.toDate(),
                false,
                now.minus(2, Calendar.HOUR).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS),
            ),
            arguments(
                now.toDate(),
                false,
                now.minus(1, Calendar.DAY_OF_MONTH).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS),
            ),

            // More precise
            arguments(
                now.toDate(),
                true,
                now.minus(900, Calendar.MILLISECOND).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS),
            ),
            arguments(
                now.toDate(),
                true,
                now.minus(59, Calendar.SECOND).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS),
            ),
            arguments(
                now.toDate(),
                true,
                now.minus(59, Calendar.MINUTE).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS),
            ),
            arguments(
                now.toDate(),
                true,
                now.minus(1, Calendar.HOUR).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES),
            ),
            arguments(
                now.toDate(),
                true,
                now.minus(2, Calendar.HOUR).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES),
            ),
            arguments(
                now.toDate(),
                true,
                now.minus(1, Calendar.DAY_OF_MONTH).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS),
            ),
            arguments(
                now.toDate(),
                true,
                now.minus(2, Calendar.DAY_OF_MONTH).toDate(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS),
            ),
        )
    }
}
