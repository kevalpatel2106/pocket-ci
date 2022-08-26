package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIInfoProvider
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.toToken
import javax.inject.Inject

internal class GithubInfoProvider @Inject constructor() : CIInfoProvider {

    override suspend fun getCIInfo(): CIInfo = CIInfo(
        type = CIType.GITHUB,
        name = R.string.github_actions,
        infoUrl = Url(ACTIONS_ABOUT_URL),
        defaultBaseUrl = Url(DEFAULT_BASE_URL),
        authTokenExplainLink = Url(TOKEN_EXPLAIN_LINK),
        sampleAuthToken = SAMPLE_AUTH_TOKEN.toToken(),
        icon = R.drawable.logo_github,

        supportViewArtifacts = true,
        supportDownloadArtifacts = true,
        supportCustomBaseUrl = false,
        supportJobs = true,
        supportBuildLevelLogs = false,
        supportJobLevelLogs = true,
    )

    companion object {
        private const val DEFAULT_BASE_URL = "https://api.github.com/"
        private const val ACTIONS_ABOUT_URL = "https://github.com/features/actions"
        private const val SAMPLE_AUTH_TOKEN = "ghp_8949b567d678f62ed81866a1cd54aaeee400"
        private const val TOKEN_EXPLAIN_LINK = "https://pocketci.page.link/github-token"
    }
}
