package com.kevalpatel2106.pocketci.initializer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TimberInitializerTest {
    private val subject = TimberInitializer()

    @Test
    fun `check timber analytics depends on analytics initializer`() {
        assertEquals(listOf(AnalyticsInitializer::class.java), subject.dependencies())
    }
}
