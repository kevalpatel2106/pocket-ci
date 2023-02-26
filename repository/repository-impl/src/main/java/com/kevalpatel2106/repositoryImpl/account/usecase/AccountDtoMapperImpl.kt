package com.kevalpatel2106.repositoryImpl.account.usecase

import com.kevalpatel2106.cache.db.accountTable.AccountDto
import com.kevalpatel2106.entity.Account
import java.util.Date
import javax.inject.Inject

internal class AccountDtoMapperImpl @Inject constructor() : AccountDtoMapper {

    override operator fun invoke(account: Account, savedAt: Date) = with(account) {
        AccountDto(
            id = localId,
            name = name,
            baseUrl = baseUrl,
            savedAt = savedAt,
            token = authToken,
            type = type,
            email = email,
            avatar = avatar,
            nextProjectCursor = null,
            projectCacheLastUpdated = 0,
        )
    }
}
