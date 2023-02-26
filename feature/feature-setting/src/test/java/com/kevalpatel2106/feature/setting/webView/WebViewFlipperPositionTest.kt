package com.kevalpatel2106.feature.setting.webView

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class WebViewFlipperPositionTest {

    @ParameterizedTest(name = "check view flipper position for {0} is {1}")
    @MethodSource("provideValues")
    fun `check title resource`(
        flipperPosition: WebViewFlipperPosition,
        expectedPosition: Int,
    ) {
        assertEquals(expectedPosition, flipperPosition.value)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: WebViewFlipperPosition, expected position
            arguments(WebViewFlipperPosition.POS_LOADING, 0),
            arguments(WebViewFlipperPosition.POS_WEB_VIEW, 1),
            arguments(WebViewFlipperPosition.POS_ERROR, 2),
        )
    }
}
