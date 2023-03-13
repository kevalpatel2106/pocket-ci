package com.kevalpatel2106.feature.artifact.list.usecase

import com.kevalpatel2106.entity.ArtifactType

internal interface ArtifactIconMapper {

    operator fun invoke(type: ArtifactType): Pair<Int, Int>
}
