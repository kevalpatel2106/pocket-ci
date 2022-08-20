package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.connector.ci.internal.CIArtifactProvider
import com.kevalpatel2106.connector.ci.internal.CIBuildInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIJobsProvider
import com.kevalpatel2106.connector.ci.internal.CIProjectInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIUserInfoProvider
import javax.inject.Inject

internal class BitriseConnector @Inject constructor(
    private val ciInfoProvider: BitriseInfoProvider,
    private val userInfoProvider: BitriseUserInfoProvider,
    private val projectInfoProvider: BitriseProjectInfoProvider,
    private val buildInfoProvider: BitriseBuildInfoProvider,
    private val buildLogsProvider: BitriseJobsProvider,
    private val artifactProvider: BitriseArtifactProvider,
) : CIConnector,
    CIInfoProvider by ciInfoProvider,
    CIUserInfoProvider by userInfoProvider,
    CIProjectInfoProvider by projectInfoProvider,
    CIBuildInfoProvider by buildInfoProvider,
    CIJobsProvider by buildLogsProvider,
    CIArtifactProvider by artifactProvider
