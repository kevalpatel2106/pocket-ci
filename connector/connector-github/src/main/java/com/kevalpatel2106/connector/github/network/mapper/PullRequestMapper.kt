package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.PullRequestDto
import com.kevalpatel2106.entity.PullRequest

internal interface PullRequestMapper {
    operator fun invoke(dto: PullRequestDto): PullRequest
}
