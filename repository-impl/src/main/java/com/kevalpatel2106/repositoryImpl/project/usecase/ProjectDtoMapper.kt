package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.cache.db.projectTable.ProjectDto
import java.util.Date

internal interface ProjectDtoMapper {

    operator fun invoke(project: Project, now: Date = Date()): ProjectDto
}
