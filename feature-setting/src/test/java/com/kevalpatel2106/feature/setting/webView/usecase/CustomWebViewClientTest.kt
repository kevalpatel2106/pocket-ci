package com.kevalpatel2106.feature.setting.webView.usecase

import com.flextrade.kfixture.KFixture
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class CustomWebViewClientTest {
    private val fixture = KFixture()
    private val callback = mock<CustomWebViewClient.Callback>()
    private val subject = CustomWebViewClient(callback)

    @Test
    fun `when page started loading then verify onPageLoading called`() {
        subject.onPageStarted(mock(), fixture(), fixture())

        verify(callback).onPageLoading()
    }

    @Test
    fun `when page finish loading then verify onPageLoaded called`() {
        subject.onPageFinished(mock(), fixture())

        verify(callback).onPageLoaded()
    }

    @Test
    fun `when error received while loading page then verify onPageLoadingFailed called`() {
        subject.onReceivedError(mock(), null, null)

        verify(callback).onPageLoadingFailed()
    }
}
