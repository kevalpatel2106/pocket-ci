package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.Commit
import com.kevalpatel2106.entity.id.CommitHash
import com.kevalpatel2106.entity.toUrlOrNull
import javax.inject.Inject

internal class CommitMapperImpl @Inject constructor() : CommitMapper {

    override fun invoke(dto: BuildDto) = with(dto) {
        if (commitHash.isNullOrBlank()) {
            null
        } else {
            Commit(
                hash = CommitHash(commitHash),
                message = commitMessage,
                author = null,
                commitAt = null,
                commitViewUrl = commitViewUrl.toUrlOrNull(),
            )
        }
    }
}
