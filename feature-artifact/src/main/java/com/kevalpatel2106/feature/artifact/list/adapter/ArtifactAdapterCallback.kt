package com.kevalpatel2106.feature.artifact.list.adapter

import com.kevalpatel2106.entity.Artifact

internal interface ArtifactAdapterCallback {
    fun onDownloadClicked(artifact: Artifact)
}
