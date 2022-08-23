package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BuildIdTest {

    @ParameterizedTest(name = "given string {0} when initialised then check build id is valid {1}")
    @MethodSource("provideValuesBuildIdValidationTest")
    fun `given string when initialised then check build id is valid or not`(
        buildIdSting: String,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { BuildId(buildIdSting) }
        } else {
            assertThrows<AssertionError> { BuildId(buildIdSting) }
        }
    }

    @Test
    fun `given string when converted to build id then build id created`() {
        val value = "test"

        val actual = value.toBuildId()

        assertEquals("test", actual.getValue())
    }

    @Test
    fun `given string when converted to build id or null then build id created`() {
        val value = "test"

        val actual = value.toBuildIdIdOrNull()

        assertEquals("test", actual?.getValue())
    }

    @Test
    fun `given null value when converted to build id or null then verify null returned`() {
        val value = null

        val actual = value.toBuildIdIdOrNull()

        assertNull(actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesBuildIdValidationTest() = listOf(
            // Format: string to test, is valid
            arguments("test", true),
            arguments("12345678", true),
            arguments("", false),
            arguments("  ", false),
        )
    }
}
