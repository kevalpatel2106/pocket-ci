package com.kevalpatel2106.repositoryImpl.account.usecase

import com.kevalpatel2106.cache.db.accountTable.AccountDto
import com.kevalpatel2106.entity.Account
import javax.inject.Inject

internal class AccountMapperImpl @Inject constructor() : AccountMapper {

    override operator fun invoke(dto: AccountDto, isSelected: Boolean) = with(dto) {
        Account(
            localId = id,
            name = name,
            email = email,
            avatar = avatar,
            type = type,
            isSelected = isSelected,
            baseUrl = baseUrl,
            authToken = token,
        )
    }
}
