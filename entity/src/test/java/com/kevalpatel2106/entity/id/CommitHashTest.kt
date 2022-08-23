package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class CommitHashTest {

    @ParameterizedTest(name = "given string {0} when initialised then check commit hash is valid {1}")
    @MethodSource("provideValuesCommitHashValidationTest")
    fun `given string when initialised then check commit hash is valid or not`(
        commitHashString: String,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { CommitHash(commitHashString) }
        } else {
            assertThrows<AssertionError> { CommitHash(commitHashString) }
        }
    }

    @Test
    fun `given commit hash when converting to short hash then short has last 8 char`() {
        val hash = CommitHash("9bd2ae8c459fe6572a54abeaaadaf0066291dad5")

        val actual = hash.shortHash

        assertEquals("6291dad5", actual)
    }

    @Test
    fun `given commit hash has 8 chars when converting to short hash then short has all 8 char`() {
        val hash = CommitHash("6291dad5")

        val actual = hash.shortHash

        assertEquals("6291dad5", actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesCommitHashValidationTest() = listOf(
            // Format: string to test, is valid
            arguments("test", false),
            arguments("1234567", false),
            arguments("", false),
            arguments("          ", false),
            arguments("123456789", true),
            arguments("12345678", true),
            arguments("9bd2ae8c459fe6572a54abeaaadaf0066291dad5", true),
        )
    }
}
