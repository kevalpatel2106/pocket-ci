package com.kevalpatel2106.builds.list.adapter

import com.kevalpatel2106.builds.list.adapter.BuildListItemType.BUILD_ITEM
import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Build

internal sealed class BuildListItem(val listItemType: BuildListItemType, val compareId: String) {

    data class BuildItem(
        val build: Build,
        val numberText: String,
        val commitHash: String?,
        val status: String,
        val triggeredTimeDifference: TimeDifferenceData,
        val executionTimeDifference: TimeDifferenceData,
    ) : BuildListItem(BUILD_ITEM, build.id.toString()) {

        constructor(build: Build) : this(
            build = build,
            numberText = "#${build.number}",
            commitHash = build.commit?.hash?.shortHash,
            status = build.status.name.lowercase(),
            triggeredTimeDifference = TimeDifferenceData(
                dateOfEventStart = build.triggeredAt,
                dateOfEventEnd = null,
            ),
            executionTimeDifference = TimeDifferenceData(
                dateOfEventStart = build.triggeredAt,
                dateOfEventEnd = build.finishedAt,
            ),
        )
    }
}
