package com.kevalpatel2106.connector.ci

import com.kevalpatel2106.connector.ci.internal.CIBuildInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIJobsProvider
import com.kevalpatel2106.connector.ci.internal.CIProjectInfoProvider
import com.kevalpatel2106.connector.ci.internal.CIUserInfoProvider

interface CIConnector :
    CIInfoProvider,
    CIUserInfoProvider,
    CIProjectInfoProvider,
    CIBuildInfoProvider,
    CIJobsProvider
