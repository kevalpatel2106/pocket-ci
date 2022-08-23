package com.kevalpatel2106.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class CITypeTest {

    @ParameterizedTest(name = "given ci type {0} check id {1}")
    @MethodSource("provideValues")
    fun `given ci type check id`(ciType: CIType, expectedId: Int) {
        assertEquals(expectedId, ciType.id)
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: ci type, expected id
            arguments(CIType.BITRISE, 1),
            arguments(CIType.GITHUB, 2),
        )
    }
}
