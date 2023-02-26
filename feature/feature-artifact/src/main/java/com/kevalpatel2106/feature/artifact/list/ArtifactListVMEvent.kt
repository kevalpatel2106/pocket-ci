package com.kevalpatel2106.feature.artifact.list

import com.kevalpatel2106.entity.DisplayError

internal sealed class ArtifactListVMEvent {
    object RefreshArtifacts : ArtifactListVMEvent()
    object Close : ArtifactListVMEvent()
    object RetryLoading : ArtifactListVMEvent()
    object ShowDownloadQueuedMessage : ArtifactListVMEvent()
    data class ShowDownloadFailed(val error: DisplayError) : ArtifactListVMEvent()
    data class ShowErrorLoadingArtifact(val error: DisplayError) : ArtifactListVMEvent()
    object ShowDownloadNotSupported : ArtifactListVMEvent()
    object ShowArtifactRemoved : ArtifactListVMEvent()
}
