package com.kevalpatel2106.feature.log.usecase

import com.flextrade.kfixture.KFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConvertToPaddedLogsImplTest {
    private val fixture = KFixture()
    private val subject = ConvertToPaddedLogsImpl()

    @Test
    fun `given logs when converted then verify padded logs`() {
        val logs = fixture<String>()

        val actual = subject(logs)

        assertEquals("\n\n$logs\n\n\n\n", actual)
    }
}
