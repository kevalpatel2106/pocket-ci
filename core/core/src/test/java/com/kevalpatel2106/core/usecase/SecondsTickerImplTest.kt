package com.kevalpatel2106.core.usecase

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SecondsTickerImplTest {
    private val subject = SecondsTickerImpl()

    @Test
    fun `should tick every second`() = runTest {
        subject().test {
            val item1 = awaitItem()
            val item2 = awaitItem()
            val item3 = awaitItem()

            assertTrue(item2 > item1)
            assertTrue(item3 > item2)
        }
    }
}
