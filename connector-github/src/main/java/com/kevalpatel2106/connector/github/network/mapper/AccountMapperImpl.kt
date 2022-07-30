package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.UserDto
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.toUrlOrNull
import javax.inject.Inject

internal class AccountMapperImpl @Inject constructor() : AccountMapper {

    override fun invoke(dto: UserDto, baseUrl: Url, token: Token) = with(dto) {
        Account(
            name = name,
            email = email,
            avatar = avatarUrl.toUrlOrNull(),
            authToken = token,
            baseUrl = baseUrl,
            type = CIType.GITHUB,
            isSelected = false,
            localId = AccountId.EMPTY,
        )
    }
}
