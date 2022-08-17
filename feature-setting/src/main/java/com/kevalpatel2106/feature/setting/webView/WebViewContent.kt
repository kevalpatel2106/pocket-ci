package com.kevalpatel2106.feature.setting.webView

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.kevalpatel2106.feature.setting.R

@Keep
enum class WebViewContent(@StringRes val title: Int, @StringRes val link: Int) {
    PRIVACY_POLICY(R.string.title_web_view_privacy_policy, R.string.privacy_policy_url),
    CHANGELOG(R.string.title_web_view_change_log, R.string.changelog_url),
}
