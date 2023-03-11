package com.kevalpatel2106.core.usecase

import app.cash.turbine.test
import com.kevalpatel2106.core.usecase.SecondsTickerImpl
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

internal class SecondsTickerImplTest {
    private val subject = SecondsTickerImpl()

    @Test
    fun `should tick every second`() =
        runBlocking { // We don't want to ignore delay here, so no runTest
            subject().test {
                val item1 = awaitItem()
                val item2 = awaitItem()
                val item3 = awaitItem()

                assertTrue(item2 - item1 >= TimeUnit.SECONDS.toMillis(1))
                assertTrue(item3 - item2 >= TimeUnit.SECONDS.toMillis(1))
            }
        }
}
