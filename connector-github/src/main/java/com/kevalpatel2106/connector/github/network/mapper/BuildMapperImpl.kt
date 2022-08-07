package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.BuildDto
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.Workflow
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.isInProgress
import java.util.Date
import javax.inject.Inject

internal class BuildMapperImpl @Inject constructor(
    private val buildStatusMapper: BuildStatusMapper,
    private val isoDateMapper: IsoDateMapper,
    private val commitMapper: CommitMapper,
    private val pullRequestMapper: PullRequestMapper,
) : BuildMapper {

    override operator fun invoke(projectId: ProjectId, dto: BuildDto): Build = with(dto) {
        val buildStatus = buildStatusMapper(this)
        Build(
            id = id.toString().toBuildId(),
            projectId = projectId,
            number = runNumber,
            finishedAt = if (!buildStatus.isInProgress()) isoDateMapper(updatedAt) else null,
            triggeredAt = runStartedAt?.let { isoDateMapper(it) } ?: Date(),
            workflow = getWorkflow(),
            status = buildStatus,
            commit = dto.headCommit?.let { commitMapper(it) },
            headBranch = headBranch,
            triggeredBy = triggeringActor.login,
            pullRequest = pullRequests.firstOrNull()?.let { pullRequestMapper(it) },
        )
    }

    private fun BuildDto.getWorkflow() = Workflow(
        id = null,
        name = name,
    )
}
