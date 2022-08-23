package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class WorkflowIdTest {

    @ParameterizedTest(name = "given string {0} when initialised then check workflow id is valid {1}")
    @MethodSource("provideValuesWorkflowIdValidationTest")
    fun `given string when initialised then check workflow id is valid or not`(
        workflowIdString: String,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { JobId(workflowIdString) }
        } else {
            assertThrows<AssertionError> { JobId(workflowIdString) }
        }
    }

    @Test
    fun `given string when converted to workflow id id then workflow id id created`() {
        val value = "test"

        val actual = value.toWorkflowId()

        assertEquals("test", actual.getValue())
    }

    @Test
    fun `given string when converted to workflow id id or null then workflow id id created`() {
        val value = "test"

        val actual = value.toWorkflowIdOrNull()

        assertEquals("test", actual?.getValue())
    }

    @Test
    fun `given null value when converted to workflow id id or null then verify null returned`() {
        val value = null

        val actual = value.toWorkflowIdOrNull()

        assertNull(actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesWorkflowIdValidationTest() = listOf(
            // Format: string to test, is valid
            arguments("test", true),
            arguments("12345678", true),
            arguments("", false),
            arguments("  ", false),
        )
    }
}
