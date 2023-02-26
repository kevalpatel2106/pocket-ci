package com.kevalpatel2106.feature.setting.webView

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.analytics.ClickEvent
import com.kevalpatel2106.feature.setting.webView.WebViewVMEvent.Close
import com.kevalpatel2106.feature.setting.webView.WebViewVMEvent.Reload
import com.kevalpatel2106.feature.setting.webView.usecase.ContentToUrlMapper
import com.kevalpatel2106.repository.AnalyticsRepo
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExtendWith(TestCoroutineExtension::class)
internal class WebViewViewModelTest {
    private val fixture = KFixture()
    private val fixedUrl = getUrlFixture(fixture)
    private val navArgs = fixture<WebViewFragmentArgs>().copy(content = WebViewContent.CHANGELOG)
    private val analyticsRepo = mock<AnalyticsRepo>()
    private val contentToUrlMapper = mock<ContentToUrlMapper> {
        on { invoke(any()) } doReturn fixedUrl
    }
    private val subject = WebViewViewModel(
        navArgs.toSavedStateHandle(),
        contentToUrlMapper,
        analyticsRepo,
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
                flipperPosition = WebViewFlipperPosition.POS_LOADING,
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
            flipperPosition = WebViewFlipperPosition.POS_WEB_VIEW,
        )
        assertEquals(expected, subject.viewState.value)
    }

    @Test
    fun `given view model initialised when page loading failed then verify error view displayed`() =
        runTest {
            subject.onPageLoadingFailed()
            advanceUntilIdle()

            val expected = WebViewViewState(
                title = navArgs.content.title,
                linkUrl = fixedUrl,
                flipperPosition = WebViewFlipperPosition.POS_ERROR,
            )
            assertEquals(expected, subject.viewState.value)
        }

    @Test
    fun `given view model initialised when reload called then verify webpage reloaded with initial link`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { testScope, flowTurbine ->
            subject.reload()
            testScope.advanceUntilIdle()

            assertEquals(Reload(fixedUrl), flowTurbine.awaitItem())
        }

    @Test
    fun `when reload called then click event logged`() {
        subject.reload()

        verify(analyticsRepo).sendEvent(ClickEvent(ClickEvent.Action.WEBVIEW_RELOAD))
    }

    @Test
    fun `given view model initialised when close then verify close event emit`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { testScope, flowTurbine ->
            subject.close()
            testScope.advanceUntilIdle()

            assertEquals(Close, flowTurbine.awaitItem())
        }
}
