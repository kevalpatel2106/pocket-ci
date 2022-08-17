package com.kevalpatel2106.repositoryImpl.cache.db.mapper

import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectBasicDto

internal interface ProjectBasicMapper {
    operator fun invoke(dto: ProjectBasicDto): ProjectBasic
}
