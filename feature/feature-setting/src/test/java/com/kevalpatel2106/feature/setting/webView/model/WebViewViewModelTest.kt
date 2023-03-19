package com.kevalpatel2106.feature.setting.webView.model

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.feature.setting.webView.WebViewContent
import com.kevalpatel2106.feature.setting.webView.WebViewFragmentArgs
import com.kevalpatel2106.feature.setting.webView.WebViewViewModel
import com.kevalpatel2106.feature.setting.webView.usecase.ContentToUrlMapper
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExtendWith(TestCoroutineExtension::class)
internal class WebViewViewModelTest {
    private val fixture = KFixture()
    private val fixedUrl = getUrlFixture(fixture)
    private val navArgs = fixture<WebViewFragmentArgs>().copy(content = WebViewContent.CHANGELOG)
    private val contentToUrlMapper = mock<ContentToUrlMapper> {
        on { invoke(any()) } doReturn fixedUrl
    }
    private val subject = WebViewViewModel(
        navArgs.toSavedStateHandle(),
        contentToUrlMapper,
    )

    @Test
    fun `when view model loaded then check initial view state`() = runTest {
        advanceUntilIdle()

        val expectedInitialState = WebViewViewState.initialState(
            title = navArgs.content.title,
            url = fixedUrl,
        )
        assertEquals(expectedInitialState, subject.viewState.value)
    }

    @Test
    fun `given view model initialised when page loading started then verify loader displayed`() =
        runTest {
            subject.onPageLoading()
            advanceUntilIdle()

            val expected = WebViewViewState(
                title = navArgs.content.title,
                linkUrl = fixedUrl,
                status = WebViewStatus.LOADING,
            )
            assertEquals(expected, subject.viewState.value)
        }

    @Test
    fun `given view model initialised when page loaded then verify web view displayed`() = runTest {
        subject.onPageLoaded()
        advanceUntilIdle()

        val expected = WebViewViewState(
            title = navArgs.content.title,
            linkUrl = fixedUrl,
            status = WebViewStatus.LOADED,
        )
        assertEquals(expected, subject.viewState.value)
    }
}
