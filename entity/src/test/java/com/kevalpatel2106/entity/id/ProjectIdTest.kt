package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ProjectIdTest {

    @ParameterizedTest(name = "given string {0} when initialised then check project id is valid {1}")
    @MethodSource("provideValuesProjectIdValidationTest")
    fun `given string when initialised then check project id is valid or not`(
        projectId: String,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { JobId(projectId) }
        } else {
            assertThrows<AssertionError> { JobId(projectId) }
        }
    }

    @Test
    fun `given string when converted to project id then project id created`() {
        val value = "test"

        val actual = value.toProjectId()

        assertEquals("test", actual.getValue())
    }

    @Test
    fun `given string when converted to project id or null then project id created`() {
        val value = "test"

        val actual = value.toProjectIdOrNull()

        assertEquals("test", actual?.getValue())
    }

    @Test
    fun `given null value when converted to project id or null then verify null returned`() {
        val value = null

        val actual = value.toProjectIdOrNull()

        assertNull(actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesProjectIdValidationTest() = listOf(
            // Format: string to test, is valid
            arguments("test", true),
            arguments("12345678", true),
            arguments("", false),
            arguments("  ", false),
        )
    }
}
