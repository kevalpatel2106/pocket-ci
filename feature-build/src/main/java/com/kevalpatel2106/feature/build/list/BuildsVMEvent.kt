package com.kevalpatel2106.feature.build.list

import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.AccountId

internal sealed class BuildsVMEvent {
    data class OpenBuild(val accountId: AccountId, val build: Build) : BuildsVMEvent()
    object RefreshBuilds : BuildsVMEvent()
    object Close : BuildsVMEvent()
    object RetryLoading : BuildsVMEvent()
}
