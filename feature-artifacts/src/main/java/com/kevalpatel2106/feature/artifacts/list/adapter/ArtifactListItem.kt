package com.kevalpatel2106.feature.artifacts.list.adapter

import androidx.annotation.DrawableRes
import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.feature.artifacts.list.adapter.ArtifactListItemType.ARTIFACT_ITEM

internal sealed class ArtifactListItem(
    val listItemType: ArtifactListItemType,
    val compareId: String,
) {

    data class ArtifactItem(
        val artifact: Artifact,
        val humanReadableSize: String?,
        @DrawableRes val artifactIcon: Int,
        val createdTimeDifferenceData: TimeDifferenceData?
    ) : ArtifactListItem(ARTIFACT_ITEM, artifact.id.getValue())
}
