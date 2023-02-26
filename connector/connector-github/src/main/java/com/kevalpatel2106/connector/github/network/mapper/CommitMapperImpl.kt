package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.CommitDto
import com.kevalpatel2106.entity.Commit
import com.kevalpatel2106.entity.id.CommitHash
import javax.inject.Inject

internal class CommitMapperImpl @Inject constructor(
    private val isoDateMapper: IsoDateMapper,
) : CommitMapper {
    override fun invoke(dto: CommitDto): Commit = with(dto) {
        Commit(
            hash = CommitHash(id),
            message = message,
            author = author.name.orEmpty(),
            commitAt = isoDateMapper(timestamp),
            commitViewUrl = null,
        )
    }
}
