package com.kevalpatel2106.feature.build.list.model

import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.id.AccountId

internal sealed class BuildListVMEvent {
    data class OpenBuild(val accountId: AccountId, val build: Build) : BuildListVMEvent()
    object Close : BuildListVMEvent()
    data class ShowErrorLoadingBuilds(val error: DisplayError) : BuildListVMEvent()
}
