package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.MeDto
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.toUrlOrNull
import javax.inject.Inject

internal class AccountMapperImpl @Inject constructor() : AccountMapper {

    override fun invoke(dto: MeDto, url: Url, token: Token) = with(dto) {
        Account(
            name = username,
            email = email,
            avatar = avatarUrl.toUrlOrNull(),
            authToken = token,
            baseUrl = url,
            type = CIType.BITRISE,
            isSelected = false,
            localId = AccountId.EMPTY,
        )
    }
}
