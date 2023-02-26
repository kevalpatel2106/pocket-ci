package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.cache.db.projectTable.ProjectWithLocalDataDto
import com.kevalpatel2106.entity.Project

internal interface ProjectWithLocalDataMapper {
    operator fun invoke(dto: ProjectWithLocalDataDto): Project
}
