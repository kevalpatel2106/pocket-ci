package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
import java.util.Date

internal interface ProjectMapper {
    operator fun invoke(dto: ProjectDto, accountId: AccountId, lastUpdatedAt: Date): Project
}
