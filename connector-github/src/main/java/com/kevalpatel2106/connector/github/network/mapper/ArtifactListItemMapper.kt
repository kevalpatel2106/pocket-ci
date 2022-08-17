package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.ArtifactDto
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.id.BuildId

internal interface ArtifactListItemMapper {

    operator fun invoke(dto: ArtifactDto, buildId: BuildId): Artifact
}
