package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.entity.ArtifactType

internal interface ArtifactTypeMapper {

    operator fun invoke(fileName: String): ArtifactType
}
