package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.BuildStatus

internal interface BuildStatusMapper {
    operator fun invoke(dto: BuildDto): BuildStatus
}
