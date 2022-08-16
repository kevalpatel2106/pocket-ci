package com.kevalpatel2106.entity

import com.kevalpatel2106.entity.id.ArtifactId
import com.kevalpatel2106.entity.id.BuildId
import java.util.Date

data class Artifact(
    val id: ArtifactId,
    val buildId: BuildId,
    val name: String,
    val type: ArtifactType,
    val sizeBytes: Long?,
    val createdAt: Date?,
)
