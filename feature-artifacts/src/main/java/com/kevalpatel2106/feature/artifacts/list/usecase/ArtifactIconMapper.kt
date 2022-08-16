package com.kevalpatel2106.feature.artifacts.list.usecase

import androidx.annotation.DrawableRes
import com.kevalpatel2106.entity.ArtifactType

internal interface ArtifactIconMapper {

    @DrawableRes
    operator fun invoke(type: ArtifactType): Int
}
