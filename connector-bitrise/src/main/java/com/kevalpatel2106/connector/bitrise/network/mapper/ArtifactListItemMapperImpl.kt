package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.ArtifactListItemDto
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.toArtifactId
import javax.inject.Inject

internal class ArtifactListItemMapperImpl @Inject constructor(
    private val artifactTypeMapper: ArtifactTypeMapper
) : ArtifactListItemMapper {

    override operator fun invoke(dto: ArtifactListItemDto, buildId: BuildId) = with(dto) {
        Artifact(
            id = slug.toArtifactId(),
            name = title,
            buildId = buildId,
            createdAt = null,
            sizeBytes = fileSizeBytes,
            type = artifactTypeMapper(title),
        )
    }
}
