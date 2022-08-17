package com.kevalpatel2106.feature.artifact.list

internal sealed class ArtifactListVMEvent {
    object RefreshArtifacts : ArtifactListVMEvent()
    object Close : ArtifactListVMEvent()
    object RetryLoading : ArtifactListVMEvent()
    object ShowDownloadQueuedMessage : ArtifactListVMEvent()
    object ShowDownloadFailed : ArtifactListVMEvent()
}
