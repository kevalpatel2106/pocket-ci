package com.kevalpatel2106.feature.setting.webView.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.feature.setting.webView.WebViewContent
import com.kevalpatel2106.repository.RemoteConfigRepo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class ContentToUrlMapperImplTest {
    private val remoteConfigRepo = mock<RemoteConfigRepo> {
        on { getChangelog() } doReturn changelogUrl
        on { getPrivacyPolicy() } doReturn privacyPolicyUrl
    }
    private val subject = ContentToUrlMapperImpl(remoteConfigRepo)

    @ParameterizedTest(name = "given content {0} when invoked then check url {1}")
    @MethodSource("provideValues")
    fun `given content when invoked then check url`(content: WebViewContent, expected: Url) {
        assertEquals(expected, subject(content))
    }

    companion object {
        private val fixture = KFixture()
        private val privacyPolicyUrl = getUrlFixture(fixture)
        private val changelogUrl = getUrlFixture(fixture)

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(WebViewContent.CHANGELOG, changelogUrl),
            arguments(WebViewContent.PRIVACY_POLICY, privacyPolicyUrl),
        )
    }
}
