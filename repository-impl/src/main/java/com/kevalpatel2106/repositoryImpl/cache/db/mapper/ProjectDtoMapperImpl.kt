package com.kevalpatel2106.repositoryImpl.cache.db.mapper

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto
import java.util.Date
import javax.inject.Inject

internal class ProjectDtoMapperImpl @Inject constructor() : ProjectDtoMapper {

    override operator fun invoke(project: Project) = with(project) {
        ProjectDto(
            name = name,
            remoteId = remoteId.getValue(),
            accountId = accountId.getValue(),
            image = image,
            repoUrl = repoUrl,
            isDisabled = isDisabled,
            isPublic = isPublic,
            owner = owner,
            lastUpdatedAt = lastUpdatedAt,
            savedAt = Date(), // Mark current time.
        )
    }
}
