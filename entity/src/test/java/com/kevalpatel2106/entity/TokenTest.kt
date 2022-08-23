package com.kevalpatel2106.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class TokenTest {

    @Test
    fun `given token with empty string when initialised check assertion error thrown`() {
        assertThrows<AssertionError> { Token("") }
    }

    @Test
    fun `given token with blank string when initialised check assertion error thrown`() {
        assertThrows<AssertionError> { Token("  ") }
    }

    @ParameterizedTest(name = "given value {0} when converted to token then check token {1}")
    @MethodSource("provideValuesForToToken")
    fun `given value when converted to token then check token`(
        value: String,
        expectedToken: Token,
    ) {
        assertEquals(expectedToken, value.toToken())
    }

    @ParameterizedTest(name = "given nullable value {0} when converted to token then check token {1}")
    @MethodSource("provideValuesForToTokenOrNull")
    fun `given nullable value when converted to token then check token`(
        value: String?,
        expectedToken: Token?,
    ) {
        assertEquals(expectedToken, value.toTokenOrNull())
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValuesForToToken() = listOf(
            // Format: value, expected token
            arguments("token", Token("token")),
            arguments("1223", Token("1223")),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForToTokenOrNull() = listOf(
            // Format: value, expected token
            arguments("token", Token("token")),
            arguments("123", Token("123")),
            arguments(null, null),
        )
    }
}
