package com.kevalpatel2106.coreViews.useCase

import app.cash.turbine.test
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.Date
import java.util.concurrent.TimeUnit

@ExtendWith(TestCoroutineExtension::class)
internal class LiveTimeDifferenceTickerImplTest {
    private val testTickInterval = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS)

    private val calculateTickInterval = mock<CalculateTickInterval> {
        on { invoke(any(), any(), any()) } doReturn testTickInterval
    }
    private val subject = LiveTimeDifferenceTickerImpl(calculateTickInterval)

    @Test
    fun `given date of event start and should tick more precise when invoked then verify tick interval calculated`() =
        runBlocking {
            val dateOfEventStart = Date()
            val shouldTickMorePrecise = false

            subject(dateOfEventStart, shouldTickMorePrecise).test {
                awaitItem()

                verify(calculateTickInterval)(
                    eq(dateOfEventStart),
                    eq(shouldTickMorePrecise),
                    any(),
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given date of event start and should tick more precise when invoked then verify first two ticks 1 seconds apart`() =
        runBlocking {
            val dateOfEventStart = Date()
            val shouldTickMorePrecise = false

            subject(dateOfEventStart, shouldTickMorePrecise).test {
                val item1 = awaitItem()
                val item2 = awaitItem()

                val difference = item2.time - item1.time
                assertTrue(difference >= testTickInterval)
                cancelAndIgnoreRemainingEvents()
            }
        }
}
