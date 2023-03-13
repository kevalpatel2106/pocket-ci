package com.kevalpatel2106.feature.artifact.list.eventHandler

import com.kevalpatel2106.feature.artifact.list.model.ArtifactListVMEvent

internal interface ArtifactListVmEventHandler {
    operator fun invoke(vmEvent: ArtifactListVMEvent)
}