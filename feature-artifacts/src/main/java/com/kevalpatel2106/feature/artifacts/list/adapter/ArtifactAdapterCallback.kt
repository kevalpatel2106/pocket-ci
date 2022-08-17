package com.kevalpatel2106.feature.artifacts.list.adapter

import com.kevalpatel2106.entity.Artifact

internal interface ArtifactAdapterCallback {
    fun onDownloadClicked(artifact: Artifact)
}
