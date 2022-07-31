package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.Commit
import com.kevalpatel2106.entity.PullRequest
import com.kevalpatel2106.entity.Workflow
import com.kevalpatel2106.entity.id.CommitHash
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toPullRequestId
import com.kevalpatel2106.entity.id.toWorkflowIdOrNull
import com.kevalpatel2106.entity.toUrlOrNull
import javax.inject.Inject

internal class BuildMapperImpl @Inject constructor(
    private val buildStatusMapper: BuildStatusMapper,
    private val isoDateMapper: IsoDateMapper,
) : BuildMapper {

    override operator fun invoke(projectId: ProjectId, dto: BuildDto): Build = with(dto) {
        Build(
            id = slug.toBuildId(),
            projectId = projectId,
            number = buildNumber,
            finishedAt = isoDateMapper(finishedAt),
            triggeredAt = isoDateMapper(triggeredAt) ?: error("Cannot parse the date $triggeredAt"),
            workflow = getWorkflow(),
            status = buildStatusMapper(this),
            commit = getCommit(),
            headBranch = branch,
            triggeredBy = triggeredBy,
            pullRequest = getPr(),
        )
    }

    private fun BuildDto.getCommit() = if (commitHash != null) {
        Commit(
            hash = CommitHash(commitHash),
            message = commitMessage,
            author = null,
            commitAt = null,
            commitViewUrl = commitViewUrl.toUrlOrNull(),
        )
    } else {
        null
    }

    private fun BuildDto.getWorkflow() = Workflow(
        id = triggeredWorkflowId.toWorkflowIdOrNull(),
        name = triggeredWorkflow,
    )

    private fun BuildDto.getPr() = if (pullRequestId != null && pullRequestId != 0L) {
        PullRequest(
            id = pullRequestId.toString().toPullRequestId(),
            number = null,
            headBranch = branch,
            baseBranch = pullRequestTargetBranch ?: error("No PR target branch found $this"),
        )
    } else {
        null
    }
}
