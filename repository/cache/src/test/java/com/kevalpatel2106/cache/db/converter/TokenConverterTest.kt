package com.kevalpatel2106.cache.db.converter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.toToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class TokenConverterTest {

    @ParameterizedTest(name = "given string {0} when converted to token then check converted token {1}")
    @MethodSource("provideTokenValues")
    fun `given string when converted to token then check converted token`(
        tokenValue: String?,
        token: Token?,
    ) {
        assertEquals(token, TokenConverter.toToken(tokenValue))
    }

    @ParameterizedTest(name = "given token {1} when converted from token then check string value {0}")
    @MethodSource("provideTokenValues")
    fun `given token when converted from token then check string value`(
        tokenValue: String?,
        token: Token?,
    ) {
        assertEquals(tokenValue, TokenConverter.fromToken(token))
    }

    companion object {
        private val fixture = KFixture()
        private val tokenFixture = fixture<String>()

        @Suppress("unused")
        @JvmStatic
        fun provideTokenValues() = mutableListOf(
            arguments(tokenFixture, tokenFixture.toToken()),
            arguments(null, null),
        )
    }
}
