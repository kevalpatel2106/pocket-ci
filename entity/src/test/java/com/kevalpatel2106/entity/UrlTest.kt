package com.kevalpatel2106.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.net.MalformedURLException

internal class UrlTest {

    @Suppress("UnusedPrivateMember")
    @ParameterizedTest(name = "given url {0} when initialised then check url is valid {1}")
    @MethodSource("provideValuesUrlValidationTest")
    fun `given url when initialised then check url is valid or not`(
        urlToTest: String,
        isValid: Boolean,
        ignore: Url?,
    ) {
        if (isValid) {
            assertDoesNotThrow { Url(urlToTest) }
        } else {
            assertThrows<MalformedURLException> { Url(urlToTest) }
        }
    }

    @Suppress("UnusedPrivateMember")
    @ParameterizedTest(name = "given string url {0} when converting to url or null then check url {2}")
    @MethodSource("provideValuesUrlValidationTest")
    fun `given string url when converting to url or null then check url`(
        urlToTest: String,
        ignore: Boolean,
        expectedUrl: Url?,
    ) {
        assertEquals(expectedUrl, urlToTest.toUrlOrNull())
    }

    @Test
    fun `check empty url value`() {
        assertEquals(Url(""), Url.EMPTY)
    }

    @Test
    fun `given url string check convert to url`() {
        assertEquals(Url("http://example.com"), "http://example.com".toUrl())
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesUrlValidationTest() = listOf(
            // Format: url to test, is valid, to url or null
            arguments("http://", true, Url("http://")),
            arguments("http://example.com", true, Url("http://example.com")),
            arguments("http://example.com/", true, Url("http://example.com/")),
            arguments("http://example.com/abc?123", true, Url("http://example.com/abc?123")),
            arguments("http://example", true, Url("http://example")),
            arguments("http://example.c", true, Url("http://example.c")),
            arguments("mailto://example.com", true, Url("mailto://example.com")),
            arguments(" ", true, null),
            arguments("", true, null),
            arguments("pocket-ci://example.com", false, null),
            arguments("rtsp://example.com", false, null),
            arguments("example.com", false, null),
            arguments("example", false, null),
        )
    }
}
