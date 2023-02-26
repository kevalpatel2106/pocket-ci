package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.StepDto
import com.kevalpatel2106.entity.Step
import com.kevalpatel2106.entity.id.JobId
import javax.inject.Inject

internal class StepMapperImpl @Inject constructor(
    private val buildStatusMapper: BuildStatusMapper,
) : StepMapper {

    override fun invoke(dto: StepDto, jobId: JobId) = with(dto) {
        Step(
            jobId = jobId,
            name = name,
            status = buildStatusMapper(conclusion, status),
        )
    }
}
