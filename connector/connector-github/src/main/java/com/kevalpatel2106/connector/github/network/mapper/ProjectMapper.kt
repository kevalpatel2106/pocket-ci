package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.ProjectDto
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId

internal interface ProjectMapper {
    operator fun invoke(dto: ProjectDto, accountId: AccountId): Project
}
