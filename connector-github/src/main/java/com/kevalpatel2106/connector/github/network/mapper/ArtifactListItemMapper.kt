package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.ArtifactListDto
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.id.BuildId

internal interface ArtifactListItemMapper {

    operator fun invoke(dto: ArtifactListDto, buildId: BuildId): Artifact
}
