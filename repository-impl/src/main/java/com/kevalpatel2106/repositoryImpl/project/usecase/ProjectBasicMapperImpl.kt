package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectBasicDto
import javax.inject.Inject

internal class ProjectBasicMapperImpl @Inject constructor() : ProjectBasicMapper {

    override operator fun invoke(dto: ProjectBasicDto) = with(dto) {
        ProjectBasic(name = name, remoteId = remoteId, accountId = accountId, owner = owner)
    }
}
