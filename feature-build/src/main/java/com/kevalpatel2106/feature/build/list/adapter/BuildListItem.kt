package com.kevalpatel2106.feature.build.list.adapter

import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.feature.build.list.adapter.BuildListItemType.BUILD_ITEM

internal sealed class BuildListItem(val listItemType: BuildListItemType, val compareId: String) {

    data class BuildItem(
        val build: Build,
        val numberText: String,
        val commitHash: String?,
        val status: String,
        val hideTriggeredBy: Boolean,
        val triggeredTimeDifference: TimeDifferenceData,
        val executionTimeDifference: TimeDifferenceData,
    ) : BuildListItem(BUILD_ITEM, build.id.toString())
}
