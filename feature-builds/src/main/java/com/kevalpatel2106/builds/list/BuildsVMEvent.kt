package com.kevalpatel2106.builds.list

import com.kevalpatel2106.entity.id.BuildId

internal sealed class BuildsVMEvent {
    data class OpenBuild(val buildId: BuildId) : BuildsVMEvent()
    object RefreshBuilds : BuildsVMEvent()
    object ShowErrorView : BuildsVMEvent()
    object RetryLoading : BuildsVMEvent()
}
