package com.kevalpatel2106.feature.artifact.list

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kevalpatel2106.entity.Artifact

internal sealed class ArtifactListItem {

    data class ArtifactItem(
        val artifact: Artifact,
        val humanReadableSize: String?,
        @DrawableRes val artifactIcon: Int,
        @StringRes val contentDescription: Int,
        val createdTime: String?,
    ) : ArtifactListItem()
}
