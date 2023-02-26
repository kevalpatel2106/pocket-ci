package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.UserDto
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url

internal interface AccountMapper {
    operator fun invoke(dto: UserDto, baseUrl: Url, token: Token): Account
}
