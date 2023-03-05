package com.kevalpatel2106.feature.artifact.list.usecase

import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem.ArtifactItem
import java.util.Date

internal interface ArtifactItemMapper {

    operator fun invoke(artifact: Artifact, now: Date = Date()): ArtifactItem
}
