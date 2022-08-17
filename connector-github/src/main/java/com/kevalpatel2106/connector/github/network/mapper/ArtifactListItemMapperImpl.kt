package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.ArtifactDto
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.toArtifactId
import javax.inject.Inject

internal class ArtifactListItemMapperImpl @Inject constructor(
    private val isoDateMapper: IsoDateMapper,
    private val artifactTypeMapper: ArtifactTypeMapper,
) : ArtifactListItemMapper {

    override fun invoke(dto: ArtifactDto, buildId: BuildId): Artifact = with(dto) {
        Artifact(
            id = id.toString().toArtifactId(),
            name = name,
            type = artifactTypeMapper(name),
            sizeBytes = sizeInBytes,
            createdAt = isoDateMapper(createdAt),
            buildId = buildId,
        )
    }
}
