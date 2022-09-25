package com.kevalpatel2106.repositoryImpl.account.usecase

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.cache.db.accountTable.AccountDto
import java.util.Date

internal interface AccountDtoMapper {

    operator fun invoke(account: Account, savedAt: Date = Date()): AccountDto
}
