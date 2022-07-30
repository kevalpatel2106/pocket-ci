package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.ProjectId

internal interface BuildMapper {

    operator fun invoke(projectId: ProjectId, dto: BuildDto): Build
}
