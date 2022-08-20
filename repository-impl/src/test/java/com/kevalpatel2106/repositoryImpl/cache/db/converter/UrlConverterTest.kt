package com.kevalpatel2106.repositoryImpl.cache.db.converter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.toUrl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class UrlConverterTest {

    @ParameterizedTest(name = "given string {0} when converted to url then check converted url {1}")
    @MethodSource("provideUrlValues")
    fun `given string when converted to url then check converted url`(
        urlValue: String?,
        url: Url?,
    ) {
        assertEquals(url, UrlConverter.toUrl(urlValue))
    }

    @ParameterizedTest(name = "given url {1} when converted from url then check string value {0}")
    @MethodSource("provideUrlValues")
    fun `given url when converted from url then check string value`(urlValue: String?, url: Url?) {
        assertEquals(urlValue, UrlConverter.fromUrl(url))
    }

    companion object {
        private val fixture = KFixture()
        private val tokenFixtureValue = "https://${fixture<String>()}.com/"

        @Suppress("unused")
        @JvmStatic
        fun provideUrlValues() = mutableListOf(
            arguments(tokenFixtureValue, tokenFixtureValue.toUrl()),
            arguments(null, null),
        )
    }
}
