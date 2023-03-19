package com.kevalpatel2106.feature.setting.webView.screen

import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevalpatel2106.core.ui.component.LoadingView
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.feature.setting.BuildConfig
import com.kevalpatel2106.feature.setting.webView.WebViewViewModel
import com.kevalpatel2106.feature.setting.webView.model.WebViewStatus.LOADING
import com.kevalpatel2106.feature.setting.webView.usecase.CustomWebViewClient

@Composable
internal fun WebviewScreen(
    viewModel: WebViewViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                WebView(ctx).setUpWebView(viewModel).apply { loadUrl(viewState.linkUrl.value) }
            },
        )
        if (viewState.status == LOADING) LoadingView()
    }
}

private fun WebView.setUpWebView(viewModel: WebViewViewModel): WebView {
    scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

    settings.loadsImagesAutomatically = true
    settings.javaScriptEnabled = false

    webViewClient = CustomWebViewClient(model = viewModel)
    if (BuildConfig.DEBUG) {
        clearCache(true)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
    }
    return this
}