package com.kevalpatel2106.pocketci.initializer

import androidx.startup.Initializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AnalyticsInitializerTest {
    private val subject = AnalyticsInitializer()

    @Test
    fun `check analytics initializer doesn't depend on anything`() {
        assertEquals(emptyList<Class<out Initializer<*>>>(), subject.dependencies())
    }
}
