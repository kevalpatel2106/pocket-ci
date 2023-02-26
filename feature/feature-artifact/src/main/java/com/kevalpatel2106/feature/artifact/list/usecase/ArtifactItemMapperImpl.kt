package com.kevalpatel2106.feature.artifact.list.usecase

import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem.ArtifactItem
import com.kevalpatel2106.feature.artifact.usecase.ArtifactIconMapper
import com.kevalpatel2106.feature.artifact.usecase.ArtifactSizeConverter
import javax.inject.Inject

internal class ArtifactItemMapperImpl @Inject constructor(
    private val artifactSizeConverter: ArtifactSizeConverter,
    private val artifactIconMapper: ArtifactIconMapper,
) : ArtifactItemMapper {

    override fun invoke(artifact: Artifact) = ArtifactItem(
        artifact = artifact,
        humanReadableSize = artifactSizeConverter(artifact.sizeBytes),
        artifactIcon = artifactIconMapper(artifact.type),
        createdTimeDifferenceData = artifact.createdAt?.let {
            TimeDifferenceData(dateOfEventStart = it, dateOfEventEnd = null)
        },
    )
}
