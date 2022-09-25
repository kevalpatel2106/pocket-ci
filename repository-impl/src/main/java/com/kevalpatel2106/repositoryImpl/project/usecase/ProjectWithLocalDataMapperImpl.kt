package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.cache.db.projectTable.ProjectWithLocalDataDto
import javax.inject.Inject

internal class ProjectWithLocalDataMapperImpl @Inject constructor() : ProjectWithLocalDataMapper {

    override operator fun invoke(dto: ProjectWithLocalDataDto) = with(dto.project) {
        Project(
            name = name,
            remoteId = remoteId,
            accountId = accountId,
            image = image,
            repoUrl = repoUrl,
            isDisabled = isDisabled,
            isPublic = isPublic,
            owner = owner,
            isPinned = dto.localData?.isPinned ?: false,
            lastUpdatedAt = lastUpdatedAt,
        )
    }
}
