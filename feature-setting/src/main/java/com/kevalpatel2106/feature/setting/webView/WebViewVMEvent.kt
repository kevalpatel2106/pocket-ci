package com.kevalpatel2106.feature.setting.webView

import androidx.annotation.StringRes

internal sealed class WebViewVMEvent {
    data class Reload(@StringRes val urlToReload: Int) : WebViewVMEvent()
    object Close : WebViewVMEvent()
}
