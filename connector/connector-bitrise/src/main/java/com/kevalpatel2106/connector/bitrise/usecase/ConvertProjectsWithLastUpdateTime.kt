package com.kevalpatel2106.connector.bitrise.usecase

import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId

internal interface ConvertProjectsWithLastUpdateTime {
    operator fun invoke(
        accountId: AccountId,
        dtos: List<ProjectDto>,
        currentMills: Long = System.currentTimeMillis(),
    ): List<Project>
}
