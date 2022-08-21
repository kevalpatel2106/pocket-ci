package com.kevalpatel2106.feature.build.list.usecase

import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.feature.build.list.adapter.BuildListItem.BuildItem
import javax.inject.Inject

internal class BuildItemMapperImpl @Inject constructor() : BuildItemMapper {

    override fun invoke(build: Build) = BuildItem(
        build = build,
        numberText = "#${build.number}",
        commitHash = build.commit?.hash?.shortHash,
        status = build.status.name.lowercase(),
        hideTriggeredBy = build.triggeredBy.isNullOrBlank(),
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
