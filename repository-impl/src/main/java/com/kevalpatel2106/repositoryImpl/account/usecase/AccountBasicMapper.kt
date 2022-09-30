package com.kevalpatel2106.repositoryImpl.account.usecase

import com.kevalpatel2106.cache.db.accountTable.AccountBasicDto
import com.kevalpatel2106.entity.AccountBasic

internal interface AccountBasicMapper {
    operator fun invoke(dto: AccountBasicDto): AccountBasic
}
