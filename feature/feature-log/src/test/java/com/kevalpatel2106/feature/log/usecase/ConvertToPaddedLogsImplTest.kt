package com.kevalpatel2106.feature.log.usecase

import com.flextrade.kfixture.KFixture
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConvertToPaddedLogsImplTest {
    private val fixture = KFixture()
    private val subject = ConvertToPaddedLogsImpl()

    @Test
    fun `given logs when converted then verify padded logs`() = runTest {
        val logs = fixture<String>()

        val actual = subject(logs)

        val expected = logs.split("\n").toMutableList().apply {
            add("\n")
            add("\n")
        }
        actual.forEachIndexed { index, line ->
            assertEquals(expected[index], line)
        }
    }
}
