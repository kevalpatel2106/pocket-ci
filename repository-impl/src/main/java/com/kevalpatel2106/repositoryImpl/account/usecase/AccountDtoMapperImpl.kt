package com.kevalpatel2106.repositoryImpl.account.usecase

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDto
import java.util.Date
import javax.inject.Inject

internal class AccountDtoMapperImpl @Inject constructor() : AccountDtoMapper {

    override operator fun invoke(account: Account) = with(account) {
        AccountDto(
            id = account.localId,
            name = name,
            baseUrl = baseUrl,
            savedAt = Date(),
            token = authToken,
            type = type,
            email = email,
            avatar = avatar,
            nextProjectCursor = null,
            projectCacheLastUpdated = 0,
        )
    }
}
