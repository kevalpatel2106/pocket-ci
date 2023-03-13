package com.kevalpatel2106.feature.artifact.list.usecase

internal interface ArtifactSizeConverter {

    operator fun invoke(sizeInBytes: Long?): String?
}
