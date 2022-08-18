package com.kevalpatel2106.repositoryImpl.account.usecase

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDto

internal interface AccountMapper {

    operator fun invoke(dto: AccountDto, isSelected: Boolean): Account
}
