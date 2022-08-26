package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.connector.bitrise.usecase.SanitizeTriggeredBy
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.Workflow
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toWorkflowIdOrNull
import com.kevalpatel2106.entity.isInProgress
import javax.inject.Inject

internal class BuildMapperImpl @Inject constructor(
    private val buildStatusMapper: BuildStatusMapper,
    private val isoDateMapper: IsoDateMapper,
    private val sanitizeTriggeredBy: SanitizeTriggeredBy,
    private val commitMapper: CommitMapper,
    private val pullRequestMapper: PullRequestMapper,
) : BuildMapper {

    override operator fun invoke(projectId: ProjectId, dto: BuildDto): Build = with(dto) {
        val buildStatus = buildStatusMapper(this)
        Build(
            id = slug.toBuildId(),
            projectId = projectId,
            number = buildNumber,
            finishedAt = if (!buildStatus.isInProgress()) isoDateMapper(finishedAt) else null,
            triggeredAt = isoDateMapper(triggeredAt) ?: error("Cannot parse the date $triggeredAt"),
            workflow = getWorkflow(),
            status = buildStatus,
            commit = commitMapper(this),
            headBranch = branch,
            triggeredBy = sanitizeTriggeredBy(triggeredBy),
            pullRequest = pullRequestMapper(this),
            abortReason = abortReason,
        )
    }

    private fun BuildDto.getWorkflow() = Workflow(
        id = triggeredWorkflowId.toWorkflowIdOrNull(),
        name = triggeredWorkflow,
    )
}
