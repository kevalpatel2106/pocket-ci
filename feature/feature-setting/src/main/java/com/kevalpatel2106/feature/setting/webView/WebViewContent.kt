package com.kevalpatel2106.feature.setting.webView

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.kevalpatel2106.core.resources.R

@Keep
enum class WebViewContent(@StringRes val title: Int) {
    PRIVACY_POLICY(R.string.title_web_view_privacy_policy),
    CHANGELOG(R.string.title_web_view_change_log),
}
