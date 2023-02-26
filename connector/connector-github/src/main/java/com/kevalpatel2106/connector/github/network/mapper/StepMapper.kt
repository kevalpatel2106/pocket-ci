package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.StepDto
import com.kevalpatel2106.entity.Step
import com.kevalpatel2106.entity.id.JobId

internal interface StepMapper {
    operator fun invoke(dto: StepDto, jobId: JobId): Step
}
