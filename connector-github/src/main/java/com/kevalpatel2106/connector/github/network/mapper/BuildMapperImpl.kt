package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.BuildDto
import com.kevalpatel2106.connector.github.network.dto.CommitDto
import com.kevalpatel2106.connector.github.network.dto.PullRequestDto
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.Commit
import com.kevalpatel2106.entity.PullRequest
import com.kevalpatel2106.entity.Workflow
import com.kevalpatel2106.entity.id.CommitHash
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.PullRequestId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toPullRequestId
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

internal class BuildMapperImpl @Inject constructor(
    private val buildStatusMapper: BuildStatusMapper,
    private val isoDateMapper: IsoDateMapper,
    private val commitMapper: CommitMapper,
) : BuildMapper {

    override operator fun invoke(projectId: ProjectId, dto: BuildDto): Build = with(dto) {
        Build(
            id = id.toString().toBuildId(),
            projectId = projectId,
            number = runNumber,
            finishedAt = null, // Not supported
            triggeredAt = runStartedAt?.let { isoDateMapper(it) } ?: Date(),
            workflow = getWorkflow(),
            status = buildStatusMapper(this),
            commit = dto.headCommit?.let { commitMapper(it) },
            headBranch = headBranch,
            triggeredBy = triggeringActor.login,
            pullRequest = pullRequests.firstOrNull()?.getPr()
        )
    }

    private fun BuildDto.getWorkflow() = Workflow(
        id = null,
        name = name,
    )

    private fun PullRequestDto.getPr() = PullRequest(
        id = id.toPullRequestId(),
        number = number,
        headBranch = head.ref,
        baseBranch = base.ref,
    )
}
