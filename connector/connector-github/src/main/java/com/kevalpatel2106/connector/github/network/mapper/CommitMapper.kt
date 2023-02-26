package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.CommitDto
import com.kevalpatel2106.entity.Commit

internal interface CommitMapper {
    operator fun invoke(dto: CommitDto): Commit
}
