package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.JobDto
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.id.BuildId

internal interface JobMapper {
    operator fun invoke(dto: JobDto, buildId: BuildId): Job
}
