package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class PullRequestIdTest {

    @ParameterizedTest(name = "given string {0} when initialised then check pull request id is valid {1}")
    @MethodSource("provideValuesPullRequestIdValidationTest")
    fun `given string when initialised then check pull request id is valid or not`(
        pullRequestIdString: String,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { PullRequestId(pullRequestIdString) }
        } else {
            assertThrows<AssertionError> { PullRequestId(pullRequestIdString) }
        }
    }

    @Test
    fun `given string when converted to pull request id then pull request id created`() {
        val value = "test"

        val actual = value.toPullRequestId()

        assertEquals("test", actual.getValue())
    }

    @Test
    fun `given string when converted to pull request id or null then pull request id created`() {
        val value = "test"

        val actual = value.toPullRequestIdOrNull()

        assertEquals("test", actual?.getValue())
    }

    @Test
    fun `given null value when converted to pull request id or null then verify null returned`() {
        val value = null

        val actual = value.toPullRequestIdOrNull()

        assertNull(actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesPullRequestIdValidationTest() = listOf(
            // Format: string to test, is valid
            arguments("test", true),
            arguments("12345678", true),
            arguments("", false),
            arguments("  ", false),
        )
    }
}
