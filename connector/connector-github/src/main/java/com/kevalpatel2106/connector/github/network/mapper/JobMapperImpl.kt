package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.JobDto
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import java.util.Date
import javax.inject.Inject

internal class JobMapperImpl @Inject constructor(
    private val isoDateMapper: IsoDateMapper,
    private val stepMapper: StepMapper,
    private val buildStatusMapper: BuildStatusMapper,
) : JobMapper {

    override fun invoke(dto: JobDto, buildId: BuildId) = with(dto) {
        val jobId = JobId(id.toString())
        Job(
            id = jobId,
            name = name,
            buildId = buildId,
            triggeredAt = isoDateMapper(startedAt) ?: Date(),
            finishedAt = isoDateMapper(completedAt),
            status = buildStatusMapper(conclusion, status),
            steps = steps.map { stepMapper(it, jobId) },
        )
    }
}
