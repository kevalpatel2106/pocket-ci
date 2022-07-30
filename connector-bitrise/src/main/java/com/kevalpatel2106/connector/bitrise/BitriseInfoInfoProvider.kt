package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.ci.internal.CIInfoProvider
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.toToken
import javax.inject.Inject

internal class BitriseInfoInfoProvider @Inject constructor() : CIInfoProvider {

    override suspend fun getCIInfo(): CIInfo = CIInfo(
        type = CIType.BITRISE,
        name = R.string.bitrise,
        infoUrl = Url(BITRISE_ABOUT_URL),
        defaultBaseUrl = Url(DEFAULT_BASE_URL),
        authTokenExplainLink = Url(TOKEN_EXPLAIN_LINK),
        sampleAuthToken = SAMPLE_AUTH_TOKEN.toToken(),
        icon = R.drawable.logo_bitrise,
        supportCustomBaseUrl = false
    )

    companion object {
        private const val DEFAULT_BASE_URL = "https://api.bitrise.io/"
        private const val BITRISE_ABOUT_URL = "https://www.bitrise.io"
        private const val SAMPLE_AUTH_TOKEN = "ABC" // TODO Replace with sample token

        // TODO Create webpage
        private const val TOKEN_EXPLAIN_LINK = "https://pocketci.page.link/bitrise-setup"
    }
}
