package com.kevalpatel2106.settings.webView

import androidx.annotation.StringRes
import com.kevalpatel2106.settings.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class WebViewContentTest {

    @Suppress("UnusedPrivateMember")
    @ParameterizedTest(name = "check title resource for {0} is {1}")
    @MethodSource("provideValues")
    fun `check title resource`(
        content: WebViewContent,
        @StringRes titleRes: Int,
        @StringRes urlRes: Int,
    ) {
        assertEquals(titleRes, content.title)
    }

    @Suppress("UnusedPrivateMember")
    @ParameterizedTest(name = "check url resource for {0} is {2}")
    @MethodSource("provideValues")
    fun `check url resource`(
        content: WebViewContent,
        @StringRes titleRes: Int,
        @StringRes urlRes: Int,
    ) {
        assertEquals(urlRes, content.link)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: web view content, expected title res, expected url res
            arguments(
                WebViewContent.PRIVACY_POLICY,
                R.string.title_web_view_privacy_policy,
                R.string.privacy_policy_url,
            ),
            arguments(
                WebViewContent.CHANGELOG,
                R.string.title_web_view_change_log,
                R.string.changelog_url,
            ),
        )
    }
}
