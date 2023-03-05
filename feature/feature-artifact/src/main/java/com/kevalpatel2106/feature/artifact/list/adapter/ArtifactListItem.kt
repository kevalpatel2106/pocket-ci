package com.kevalpatel2106.feature.artifact.list.adapter

import androidx.annotation.DrawableRes
import com.kevalpatel2106.entity.Artifact

internal sealed class ArtifactListItem {

    data class ArtifactItem(
        val artifact: Artifact,
        val humanReadableSize: String?,
        @DrawableRes val artifactIcon: Int,
        val createdTime: String?,
    ) : ArtifactListItem()
}
