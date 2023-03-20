package com.kevalpatel2106.feature.job.list.usecase

import com.kevalpatel2106.core.usecase.TimeDifferenceFormatter
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.feature.job.list.model.JobListItem.JobItem
import java.util.Date
import javax.inject.Inject

internal class JobItemMapperImpl @Inject constructor(
    private val timeDifferenceFormatter: TimeDifferenceFormatter,
) : JobItemMapper {

    override fun invoke(job: Job, now: Date) = JobItem(
        job = job,
        triggeredTime = timeDifferenceFormatter(
            dateStart = job.triggeredAt,
            dateEnd = now,
            appendText = "ago", // TODO convert to use string resource
            showMorePrecise = false,
        ),
        executionTime = timeDifferenceFormatter(
            dateStart = job.triggeredAt,
            dateEnd = job.finishedAt ?: now,
            showMorePrecise = true,
        ),
    )
}
