package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.connector.ci.internal.CIArtifactProvider
import com.kevalpatel2106.connector.ci.internal.CIBuildInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIJobsProvider
import com.kevalpatel2106.connector.ci.internal.CIProjectInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIUserInfoProvider
import javax.inject.Inject

internal class GitHubConnector @Inject constructor(
    private val ciInfoProvider: GithubInfoProvider,
    private val userInfoProvider: GitHubUserInfoProvider,
    private val projectInfoProvider: GitHubProjectInfoProvider,
    private val buildInfoProvider: GitHubBuildInfoProvider,
    private val buildLogsProvider: GitHubJobsProvider,
    private val artifactProvider: GithubArtifactProvider,
) : CIConnector,
    CIInfoProvider by ciInfoProvider,
    CIUserInfoProvider by userInfoProvider,
    CIProjectInfoProvider by projectInfoProvider,
    CIBuildInfoProvider by buildInfoProvider,
    CIJobsProvider by buildLogsProvider,
    CIArtifactProvider by artifactProvider
