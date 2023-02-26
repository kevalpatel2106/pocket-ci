package com.kevalpatel2106.feature.setting.webView

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class WebViewViewStateTest {
    private val fixture = KFixture()

    @Test
    fun `when getting initial view state then verify initial state`() {
        val title = fixture<Int>()
        val url = getUrlFixture(fixture)

        val actual = WebViewViewState.initialState(title, url)

        val expected = WebViewViewState(title, url, WebViewFlipperPosition.POS_LOADING)
        assertEquals(expected, actual)
    }
}
