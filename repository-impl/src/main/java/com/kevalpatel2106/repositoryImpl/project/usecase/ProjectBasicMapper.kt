package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.cache.db.projectTable.ProjectBasicDto
import com.kevalpatel2106.entity.ProjectBasic

internal interface ProjectBasicMapper {
    operator fun invoke(dto: ProjectBasicDto): ProjectBasic
}
