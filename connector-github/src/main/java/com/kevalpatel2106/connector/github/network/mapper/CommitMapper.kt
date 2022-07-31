package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.CommitDto
import com.kevalpatel2106.connector.github.network.dto.UserDto
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.Commit
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url

internal interface CommitMapper {
    operator fun invoke(dto: CommitDto): Commit
}
