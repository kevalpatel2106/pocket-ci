package com.kevalpatel2106.feature.build.list

import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.AccountId

internal sealed class BuildListVMEvent {
    data class OpenBuild(val accountId: AccountId, val build: Build) : BuildListVMEvent()
    object RefreshBuildList : BuildListVMEvent()
    object Close : BuildListVMEvent()
    object RetryLoading : BuildListVMEvent()
}
