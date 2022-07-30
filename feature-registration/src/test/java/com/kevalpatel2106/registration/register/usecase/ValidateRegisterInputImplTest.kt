package com.kevalpatel2106.registration.register.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class ValidateRegisterInputImplTest {

    @ParameterizedTest(name = "given url {0} and token {1} when validating inputs then check validation results")
    @MethodSource("provideValues")
    fun `given url and token when validating inputs then check validation result`(
        url: String,
        token: String,
        isValidUrlExpected: Boolean,
        isValidTokenExpected: Boolean,
    ) {
        val (isValidUrlResult, isValidTokenResult) = ValidateRegisterInputImpl()(url, token)

        assertEquals(isValidUrlResult, isValidUrlExpected)
        assertEquals(isValidTokenResult, isValidTokenExpected)
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: url, token, is valid url, is valid token

            // check url validation
            arguments("http://example.com   ", "token", false, true),
            arguments("  http://example.com  ", "token", false, true),
            arguments("example.com", "token", false, true),
            arguments("", "token", false, true),
            arguments("   ", "token", false, true),
            arguments("http://example.com", "token", false, true),
            arguments("http://example.com/", "token", true, true),
            arguments("https://example.com", "token", false, true),
            arguments("https://www.example.com", "token", false, true),
            arguments("https://www.example.com/", "token", true, true),

            // check token sanitization
            arguments("http://example.com/", "token  ", true, false),
            arguments("http://example.com/", "  token  ", true, false),
            arguments("http://example.com/", "  ", true, false),
            arguments("http://example.com/", "", true, false),
            arguments("http://example.com/", "token", true, true),
        )
    }
}
