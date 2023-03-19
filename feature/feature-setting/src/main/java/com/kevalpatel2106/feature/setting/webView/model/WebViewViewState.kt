package com.kevalpatel2106.feature.setting.webView.model

import androidx.annotation.StringRes
import com.kevalpatel2106.entity.Url

internal data class WebViewViewState(
    @StringRes val title: Int,
    val linkUrl: Url,
    val status: WebViewStatus,
) {
    companion object {
        fun initialState(@StringRes title: Int, url: Url) = WebViewViewState(
            title = title,
            linkUrl = url,
            status = WebViewStatus.LOADING,
        )
    }
}
