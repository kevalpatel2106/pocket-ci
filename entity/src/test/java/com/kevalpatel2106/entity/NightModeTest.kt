package com.kevalpatel2106.entity

import androidx.appcompat.app.AppCompatDelegate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class NightModeTest {

    @ParameterizedTest(name = "given night mode {0} check value {1}")
    @MethodSource("provideValues")
    fun `given night mode check value`(
        nightMode: NightMode,
        @AppCompatDelegate.NightMode expectedValue: Int,
    ) {
        assertEquals(expectedValue, nightMode.value)
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: night mode, expected value
            arguments(NightMode.ON, AppCompatDelegate.MODE_NIGHT_YES),
            arguments(NightMode.OFF, AppCompatDelegate.MODE_NIGHT_NO),
            arguments(NightMode.AUTO, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
        )
    }
}
