package com.kevalpatel2106.feature.setting.webView.usecase

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

internal class CustomWebViewClient(private val model: Callback) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        model.onPageLoading()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        model.onPageLoaded()
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?,
    ) {
        super.onReceivedError(view, request, error)
        model.onPageLoadingFailed()
    }

    interface Callback {
        fun onPageLoading()
        fun onPageLoaded()
        fun onPageLoadingFailed()
    }
}
