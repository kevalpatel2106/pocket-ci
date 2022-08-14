package com.kevalpatel2106.settings.webView

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.settings.webView.WebViewVMEvent.Close
import com.kevalpatel2106.settings.webView.WebViewVMEvent.Reload
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WebViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<WebViewVMEvent>(), CustomWebViewClient.Callback {
    private val navArgs = WebViewFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow(
        WebViewViewState.initialState(navArgs.content.title, navArgs.content.link),
    )
    val viewState = _viewState.asStateFlow()

    override fun onPageLoading() {
        _viewState.modify(viewModelScope) {
            copy(flipperPosition = WebViewFlipperPosition.POS_LOADING)
        }
    }

    override fun onPageLoaded() {
        _viewState.modify(viewModelScope) {
            copy(flipperPosition = WebViewFlipperPosition.POS_WEB_VIEW)
        }
    }

    override fun onPageLoadingFailed() {
        _viewState.modify(viewModelScope) {
            copy(flipperPosition = WebViewFlipperPosition.POS_ERROR)
        }
    }

    fun close() = viewModelScope.launch { _vmEventsFlow.emit(Close) }

    fun reload() {
        viewModelScope.launch { _vmEventsFlow.emit(Reload(navArgs.content.link)) }
    }
}
