package com.kevalpatel2106.feature.setting.webView

import androidx.annotation.StringRes
import com.kevalpatel2106.entity.Url

internal data class WebViewViewState(
    @StringRes val title: Int,
    val linkUrl: Url,
    val flipperPosition: WebViewFlipperPosition,
) {
    companion object {
        fun initialState(@StringRes title: Int, url: Url) = WebViewViewState(
            title = title,
            linkUrl = url,
            flipperPosition = WebViewFlipperPosition.POS_LOADING,
        )
    }
}
