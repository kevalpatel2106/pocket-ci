package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectWithLocalDataDto

internal interface ProjectWithLocalDataMapper {
    operator fun invoke(dto: ProjectWithLocalDataDto): Project
}
