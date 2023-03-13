package com.kevalpatel2106.feature.artifact.list.eventHandler

import com.kevalpatel2106.feature.artifact.list.model.ArtifactListViewState

internal interface ArtifactListViewStateHandler {
    operator fun invoke(viewState: ArtifactListViewState)
}