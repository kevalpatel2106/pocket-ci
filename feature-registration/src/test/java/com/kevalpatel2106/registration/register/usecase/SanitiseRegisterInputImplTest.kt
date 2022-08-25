package com.kevalpatel2106.registration.register.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class SanitiseRegisterInputImplTest {

    @ParameterizedTest(name = "given url {0} and token {1} when sanitise inputs then check sanitised values")
    @MethodSource("provideValues")
    fun `given url and token when sanitised then check values`(
        url: String,
        token: String,
        expectedUrl: String,
        expectedToken: String,
    ) {
        val (resultUrl, resultToken) = SanitiseRegisterInputImpl()(url, token)

        assertEquals(resultUrl, expectedUrl)
        assertEquals(resultToken, expectedToken)
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: url, token, sanitised token, sanitised token

            // check url sanitization
            arguments("http://example.com   ", "token", "http://example.com", "token"),
            arguments("  http://example.com  ", "token", "http://example.com", "token"),
            arguments("http://example.com", "token", "http://example.com", "token"),

            // check token sanitization
            arguments("http://example.com", "token  ", "http://example.com", "token"),
            arguments("http://example.com", "  token  ", "http://example.com", "token"),
            arguments("http://example.com", "token", "http://example.com", "token"),

            // check both sanitization
            arguments("  http://example.com", "token  ", "http://example.com", "token"),
        )
    }
}
