package com.kevalpatel2106.repositoryImpl.account.usecase

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDto

internal interface AccountDtoMapper {

    operator fun invoke(account: Account): AccountDto
}
