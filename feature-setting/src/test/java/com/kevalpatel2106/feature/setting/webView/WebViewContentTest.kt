package com.kevalpatel2106.feature.setting.webView

import androidx.annotation.StringRes
import com.kevalpatel2106.feature.setting.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class WebViewContentTest {

    @Suppress("UnusedPrivateMember")
    @ParameterizedTest(name = "check title resource for {0} is {1}")
    @MethodSource("provideValues")
    fun `check title resource`(content: WebViewContent, @StringRes titleRes: Int) {
        assertEquals(titleRes, content.title)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: web view content, expected title res, expected url res
            arguments(WebViewContent.PRIVACY_POLICY, R.string.title_web_view_privacy_policy),
            arguments(WebViewContent.CHANGELOG, R.string.title_web_view_change_log),
        )
    }
}
