package com.kevalpatel2106.feature.setting.webView

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
class WebViewViewModelTest {
    private val fixture = KFixture()
    private val navArgs = fixture<WebViewFragmentArgs>()
    private val subject = WebViewViewModel(navArgs.toSavedStateHandle())

    @Test
    fun `when view model loaded then check initial view state`() = runTest {
        advanceUntilIdle()
        val expectedInitialState = WebViewViewState.initialState(
            title = navArgs.content.title,
            linkUrl = navArgs.content.link,
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
                linkUrl = navArgs.content.link,
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
            linkUrl = navArgs.content.link,
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
                linkUrl = navArgs.content.link,
                flipperPosition = WebViewFlipperPosition.POS_ERROR,
            )
            assertEquals(expected, subject.viewState.value)
        }

    @Test
    fun `given view model initialised when reload called then verify webpage reloaded with initial link`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { testScope, flowTurbine ->
            subject.reload()
            testScope.advanceUntilIdle()

            assertEquals(WebViewVMEvent.Reload(navArgs.content.link), flowTurbine.awaitItem())
        }
}
