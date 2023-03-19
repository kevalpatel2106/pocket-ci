package com.kevalpatel2106.feature.build.list.usecase

import com.kevalpatel2106.core.usecase.TimeDifferenceFormatter
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.feature.build.list.model.BuildListItem.BuildItem
import java.util.Date
import javax.inject.Inject

internal class BuildItemMapperImpl @Inject constructor(
    private val timeDifferenceFormatter: TimeDifferenceFormatter,
) : BuildItemMapper {

    override fun invoke(build: Build, now: Date) = BuildItem(
        buildId = build.id,
        buildStatus = build.status,
        numberText = "#${build.number}",
        workflowName = build.workflow.name,
        headBranch = build.headBranch,
        commitMessage = build.commit?.message,
        commitHash = build.commit?.hash?.shortHash,
        triggeredBy = build.triggeredBy,
        triggeredTime = timeDifferenceFormatter(
            dateStart = build.triggeredAt,
            dateEnd = now,
            appendText = "ago", // TODO convert to use string resource
            showMorePrecise = false,
        ),
        executionTime = timeDifferenceFormatter(
            dateStart = build.triggeredAt,
            dateEnd = build.finishedAt ?: now,
            showMorePrecise = true,
        ),
    ).apply { this.build = build }
}
