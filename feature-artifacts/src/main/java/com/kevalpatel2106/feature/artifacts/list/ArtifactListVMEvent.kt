package com.kevalpatel2106.feature.artifacts.list

internal sealed class ArtifactListVMEvent {
    object RefreshArtifacts : ArtifactListVMEvent()
    object Close : ArtifactListVMEvent()
    object RetryLoading : ArtifactListVMEvent()
}
