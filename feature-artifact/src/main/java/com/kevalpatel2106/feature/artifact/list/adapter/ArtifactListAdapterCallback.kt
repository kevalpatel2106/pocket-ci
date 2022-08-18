package com.kevalpatel2106.feature.artifact.list.adapter

import com.kevalpatel2106.entity.Artifact

internal interface ArtifactListAdapterCallback {
    fun onDownloadClicked(artifact: Artifact)
}
