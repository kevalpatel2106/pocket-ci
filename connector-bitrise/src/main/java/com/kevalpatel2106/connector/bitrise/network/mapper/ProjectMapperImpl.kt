package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.entity.toUrlOrNull
import java.util.Date
import javax.inject.Inject

internal class ProjectMapperImpl @Inject constructor() : ProjectMapper {

    override fun invoke(dto: ProjectDto, accountId: AccountId, lastUpdatedAt: Date) = with(dto) {
        Project(
            remoteId = slug.toProjectId(),
            accountId = accountId,
            name = title,
            owner = repoOwner,
            image = avatarUrl.toUrlOrNull(),
            repoUrl = repoUrl.toUrlOrNull(),
            isDisabled = isDisabled,
            isPublic = isPublic,
            isPinned = false,
            lastUpdatedAt = lastUpdatedAt,
        )
    }
}
