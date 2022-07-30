package com.kevalpatel2106.repositoryImpl.cache.db.mapper

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto
import javax.inject.Inject

internal class ProjectMapperImpl @Inject constructor() : ProjectMapper {

    override operator fun invoke(dto: ProjectDto) = with(dto) {
        Project(
            name = name,
            remoteId = remoteId.toProjectId(),
            accountId = accountId.toAccountId(),
            image = image,
            repoUrl = repoUrl,
            isDisabled = isDisabled,
            isPublic = isPublic,
            owner = owner,
            isFavourite = false,
            lastUpdatedAt = lastUpdatedAt,
        )
    }
}
