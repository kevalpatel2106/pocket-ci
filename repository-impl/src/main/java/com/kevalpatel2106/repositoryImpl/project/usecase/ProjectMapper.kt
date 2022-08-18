package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto

internal interface ProjectMapper {
    operator fun invoke(dto: ProjectDto): Project
}
