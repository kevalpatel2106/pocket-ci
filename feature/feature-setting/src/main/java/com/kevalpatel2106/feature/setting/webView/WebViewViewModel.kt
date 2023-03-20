package com.kevalpatel2106.feature.setting.webView

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kevalpatel2106.feature.setting.webView.model.WebViewStatus
import com.kevalpatel2106.feature.setting.webView.model.WebViewViewState
import com.kevalpatel2106.feature.setting.webView.usecase.ContentToUrlMapper
import com.kevalpatel2106.feature.setting.webView.usecase.CustomWebViewClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class WebViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    contentToUrlMapper: ContentToUrlMapper,
) : ViewModel(), CustomWebViewClient.Callback {
    private val navArgs = WebViewFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow(
        WebViewViewState.initialState(navArgs.content.title, contentToUrlMapper(navArgs.content)),
    )
    val viewState = _viewState.asStateFlow()

    override fun onPageLoading() {
        _viewState.update { it.copy(status = WebViewStatus.LOADING) }
    }

    override fun onPageLoaded() {
        _viewState.update { it.copy(status = WebViewStatus.LOADED) }
    }

    override fun onPageLoadingFailed() {
        _viewState.update { it.copy(status = WebViewStatus.LOADED) }
    }
}
