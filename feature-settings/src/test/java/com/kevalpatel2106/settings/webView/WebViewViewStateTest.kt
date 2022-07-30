package com.kevalpatel2106.settings.webView

import com.flextrade.kfixture.KFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WebViewViewStateTest {
    private val fixture = KFixture()

    @Test
    fun `when getting initial view state then check default position is loading`() {
        val actual = WebViewViewState.initialState(fixture(), fixture()).flipperPosition

        assertEquals(WebViewFlipperPosition.POS_LOADING, actual)
    }
}
