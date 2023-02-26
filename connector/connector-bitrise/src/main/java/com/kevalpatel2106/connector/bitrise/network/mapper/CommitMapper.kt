package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.Commit

internal interface CommitMapper {
    operator fun invoke(dto: BuildDto): Commit?
}
