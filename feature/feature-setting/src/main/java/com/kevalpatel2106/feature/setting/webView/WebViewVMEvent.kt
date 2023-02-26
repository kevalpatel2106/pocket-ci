package com.kevalpatel2106.feature.setting.webView

import com.kevalpatel2106.entity.Url

internal sealed class WebViewVMEvent {
    data class Reload(val urlToReload: Url) : WebViewVMEvent()
    object Close : WebViewVMEvent()
}
