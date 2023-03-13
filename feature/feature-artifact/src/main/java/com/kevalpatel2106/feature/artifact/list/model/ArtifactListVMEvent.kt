package com.kevalpatel2106.feature.artifact.list.model

import com.kevalpatel2106.entity.DisplayError

internal sealed class ArtifactListVMEvent {
    object Close : ArtifactListVMEvent()
    object ShowDownloadQueuedMessage : ArtifactListVMEvent()
    data class ShowDownloadFailed(val error: DisplayError) : ArtifactListVMEvent()
    object ShowDownloadNotSupported : ArtifactListVMEvent()
    object ShowArtifactRemoved : ArtifactListVMEvent()
}
