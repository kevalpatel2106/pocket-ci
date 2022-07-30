package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.toUrlOrNull
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

internal class BuildMapperImpl @Inject constructor(
    private val buildStatusMapper: BuildStatusMapper,
) : BuildMapper {

    override operator fun invoke(projectId: ProjectId, dto: BuildDto): Build = with(dto) {
        Build(
            id = slug.toBuildId(),
            projectId = projectId,
            number = buildNumber,
            finishedAt = finishedAt?.let { format(it) },
            triggeredAt = format(triggeredAt) ?: error("Cannot parse the date $triggeredAt"),
            workflow = triggeredWorkflow,
            status = buildStatusMapper(this),
            commitAt = null, // Not supported
            commitAuthor = null, // Not supported
            commitHash = commitHash,
            commitMessage = commitMessage,
            commitViewUrl = commitViewUrl.toUrlOrNull(),
            headBranch = branch,
            triggeredBy = triggeredBy,
        )
    }

    private fun format(date: String): Date? = SimpleDateFormat(ISO_8601_TIME_FORMAT).parse(date)

    companion object {
        private const val ISO_8601_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}
