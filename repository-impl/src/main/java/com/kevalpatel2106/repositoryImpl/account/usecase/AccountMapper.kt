package com.kevalpatel2106.repositoryImpl.account.usecase

import com.kevalpatel2106.cache.db.accountTable.AccountDto
import com.kevalpatel2106.entity.Account

internal interface AccountMapper {

    operator fun invoke(dto: AccountDto, isSelected: Boolean): Account
}
