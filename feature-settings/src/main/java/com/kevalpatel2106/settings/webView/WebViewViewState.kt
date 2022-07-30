package com.kevalpatel2106.settings.webView

import androidx.annotation.StringRes

internal data class WebViewViewState(
    @StringRes val title: Int,
    @StringRes val linkUrl: Int,
    val flipperPosition: WebViewFlipperPosition,
) {
    companion object {
        fun initialState(
            @StringRes title: Int,
            @StringRes linkUrl: Int,
        ): WebViewViewState {
            return WebViewViewState(
                title = title,
                linkUrl = linkUrl,
                flipperPosition = WebViewFlipperPosition.POS_LOADING,
            )
        }
    }
}
