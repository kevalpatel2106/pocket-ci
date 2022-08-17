package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.ArtifactListItemDto
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.id.BuildId

internal interface ArtifactListItemMapper {

    operator fun invoke(dto: ArtifactListItemDto, buildId: BuildId): Artifact
}
