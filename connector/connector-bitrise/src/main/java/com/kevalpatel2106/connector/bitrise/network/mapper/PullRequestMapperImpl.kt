package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.PullRequest
import com.kevalpatel2106.entity.id.toPullRequestId
import javax.inject.Inject

internal class PullRequestMapperImpl @Inject constructor() : PullRequestMapper {

    override fun invoke(dto: BuildDto) = with(dto) {
        if (pullRequestId != null && pullRequestId != 0L) {
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
}
