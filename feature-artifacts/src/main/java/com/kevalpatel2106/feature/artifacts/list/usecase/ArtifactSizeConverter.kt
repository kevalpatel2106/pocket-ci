package com.kevalpatel2106.feature.artifacts.list.usecase

internal interface ArtifactSizeConverter {

    operator fun invoke(sizeInBytes: Long?): String?
}
