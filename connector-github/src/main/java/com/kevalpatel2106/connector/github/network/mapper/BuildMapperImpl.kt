package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.BuildDto
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.toBuildId
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

internal class BuildMapperImpl @Inject constructor(
    private val buildStatusMapper: BuildStatusMapper,
) : BuildMapper {

    override operator fun invoke(projectId: ProjectId, dto: BuildDto): Build = with(dto) {
        Build(
            id = id.toString().toBuildId(),
            projectId = projectId,
            number = runNumber,
            finishedAt = null, // Not supported
            triggeredAt = runStartedAt?.let { format(it) } ?: Date(),
            workflow = name,
            status = buildStatusMapper(this),
            commitAt = format(headCommit.timestamp),
            commitAuthor = headCommit.author.name.orEmpty(),
            commitHash = headCommit.id,
            commitMessage = headCommit.message,
            commitViewUrl = null, // Not supported
            headBranch = headBranch,
            triggeredBy = actor.login,
        )
    }

    private fun format(date: String?) = date?.let {
        SimpleDateFormat(ISO_8601_TIME_FORMAT).parse(it)
    }

    companion object {
        private const val ISO_8601_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}
