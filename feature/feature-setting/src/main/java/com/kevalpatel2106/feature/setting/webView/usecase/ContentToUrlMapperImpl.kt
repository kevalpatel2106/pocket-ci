package com.kevalpatel2106.feature.setting.webView.usecase

import com.kevalpatel2106.feature.setting.webView.WebViewContent
import com.kevalpatel2106.repository.RemoteConfigRepo
import javax.inject.Inject

class ContentToUrlMapperImpl @Inject constructor(
    private val remoteConfigRepo: RemoteConfigRepo,
) : ContentToUrlMapper {

    override fun invoke(content: WebViewContent) = when (content) {
        WebViewContent.PRIVACY_POLICY -> remoteConfigRepo.getPrivacyPolicy()
        WebViewContent.CHANGELOG -> remoteConfigRepo.getChangelog()
    }
}
