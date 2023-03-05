package com.kevalpatel2106.feature.setting.webView

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.entity.analytics.ClickEvent
import com.kevalpatel2106.feature.setting.webView.WebViewVMEvent.Close
import com.kevalpatel2106.feature.setting.webView.WebViewVMEvent.Reload
import com.kevalpatel2106.feature.setting.webView.usecase.ContentToUrlMapper
import com.kevalpatel2106.feature.setting.webView.usecase.CustomWebViewClient
import com.kevalpatel2106.repository.AnalyticsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WebViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    contentToUrlMapper: ContentToUrlMapper,
    private val analyticsRepo: AnalyticsRepo,
) : ViewModel(), CustomWebViewClient.Callback {
    private val navArgs = WebViewFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _vmEventsFlow = MutableSharedFlow<WebViewVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow(
        WebViewViewState.initialState(navArgs.content.title, contentToUrlMapper(navArgs.content)),
    )
    val viewState = _viewState.asStateFlow()

    override fun onPageLoading() {
        _viewState.update { it.copy(flipperPosition = WebViewFlipperPosition.POS_LOADING) }
    }

    override fun onPageLoaded() {
        _viewState.update { it.copy(flipperPosition = WebViewFlipperPosition.POS_WEB_VIEW) }
    }

    override fun onPageLoadingFailed() {
        _viewState.update { it.copy(flipperPosition = WebViewFlipperPosition.POS_ERROR) }
    }

    fun close() = viewModelScope.launch { _vmEventsFlow.emit(Close) }

    fun reload() {
        analyticsRepo.sendEvent(ClickEvent(ClickEvent.Action.WEBVIEW_RELOAD))
        viewModelScope.launch { _vmEventsFlow.emit(Reload(_viewState.value.linkUrl)) }
    }
}
