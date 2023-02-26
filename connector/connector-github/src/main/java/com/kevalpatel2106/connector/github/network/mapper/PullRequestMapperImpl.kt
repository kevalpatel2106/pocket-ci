package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.PullRequestDto
import com.kevalpatel2106.entity.PullRequest
import com.kevalpatel2106.entity.id.toPullRequestId
import javax.inject.Inject

internal class PullRequestMapperImpl @Inject constructor() : PullRequestMapper {

    override fun invoke(dto: PullRequestDto) = with(dto) {
        PullRequest(
            id = id.toPullRequestId(),
            number = number,
            headBranch = head.ref,
            baseBranch = base.ref,
        )
    }
}
