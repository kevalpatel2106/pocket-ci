package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class JobIdTest {

    @ParameterizedTest(name = "given string {0} when initialised then check job id is valid {1}")
    @MethodSource("provideValuesJobIdValidationTest")
    fun `given string when initialised then check job id is valid or not`(
        jobIdString: String,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { JobId(jobIdString) }
        } else {
            assertThrows<AssertionError> { JobId(jobIdString) }
        }
    }

    @Test
    fun `given string when converted to job id then job id created`() {
        val value = "test"

        val actual = value.toJobId()

        assertEquals("test", actual.getValue())
    }

    @Test
    fun `given string when converted to job id or null then job id created`() {
        val value = "test"

        val actual = value.toJobIdOrNull()

        assertEquals("test", actual?.getValue())
    }

    @Test
    fun `given null value when converted to job id or null then verify null returned`() {
        val value = null

        val actual = value.toJobIdOrNull()

        assertNull(actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesJobIdValidationTest() = listOf(
            // Format: string to test, is valid
            arguments("test", true),
            arguments("12345678", true),
            arguments("", false),
            arguments("  ", false),
        )
    }
}
