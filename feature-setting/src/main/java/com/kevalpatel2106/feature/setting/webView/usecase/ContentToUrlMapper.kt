package com.kevalpatel2106.feature.setting.webView.usecase

import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.feature.setting.webView.WebViewContent

interface ContentToUrlMapper {
    operator fun invoke(content: WebViewContent): Url
}
