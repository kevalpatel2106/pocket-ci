package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.ProjectDto
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.entity.toUrlOrNull
import java.util.Date
import javax.inject.Inject

internal class ProjectMapperImpl @Inject constructor(
    private val isoDateMapper: IsoDateMapper,
) : ProjectMapper {

    override fun invoke(dto: ProjectDto, accountId: AccountId) = with(dto) {
        Project(
            remoteId = id.toString().toProjectId(),
            accountId = accountId,
            name = name,
            owner = owner.login,
            image = null,
            repoUrl = htmlUrl.toUrlOrNull(),
            isDisabled = disabled,
            isPublic = visibility == PUBLIC_VISIBILITY,
            isPinned = false,
            lastUpdatedAt = isoDateMapper(updatedAt) ?: Date(),
        )
    }

    companion object {
        private const val PUBLIC_VISIBILITY = "public"
    }
}
