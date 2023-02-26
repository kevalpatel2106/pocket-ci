package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.MeDto
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url

internal interface AccountMapper {

    operator fun invoke(dto: MeDto, url: Url, token: Token): Account
}
