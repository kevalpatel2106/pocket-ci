package com.kevalpatel2106.feature.artifact.list.usecase

import com.kevalpatel2106.core.usecase.TimeDifferenceFormatter
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem.ArtifactItem
import com.kevalpatel2106.feature.artifact.usecase.ArtifactIconMapper
import com.kevalpatel2106.feature.artifact.usecase.ArtifactSizeConverter
import java.util.Date
import javax.inject.Inject

internal class ArtifactItemMapperImpl @Inject constructor(
    private val artifactSizeConverter: ArtifactSizeConverter,
    private val artifactIconMapper: ArtifactIconMapper,
    private val timeDifferenceFormatter: TimeDifferenceFormatter,
) : ArtifactItemMapper {

    override fun invoke(artifact: Artifact, now: Date) = ArtifactItem(
        artifact = artifact,
        humanReadableSize = artifactSizeConverter(artifact.sizeBytes),
        artifactIcon = artifactIconMapper(artifact.type),
        createdTime = artifact.createdAt?.let {
            timeDifferenceFormatter(
                dateStart = it,
                dateEnd = now,
                appendText = "ago", // TODO convert to use string resource
                showMorePrecise = false,
            )
        },
    )
}
