package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto

internal interface ProjectDtoMapper {

    operator fun invoke(project: Project): ProjectDto
}
