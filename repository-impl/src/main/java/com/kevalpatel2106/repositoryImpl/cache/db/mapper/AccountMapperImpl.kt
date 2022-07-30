package com.kevalpatel2106.repositoryImpl.cache.db.mapper

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.toToken
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDto
import javax.inject.Inject

internal class AccountMapperImpl @Inject constructor() : AccountMapper {

    override operator fun invoke(dto: AccountDto, isSelected: Boolean) = with(dto) {
        Account(
            localId = id.toAccountId(),
            name = name,
            email = email,
            avatar = avatar,
            type = type,
            isSelected = isSelected,
            baseUrl = baseUrl,
            authToken = token.toToken(),
        )
    }
}
