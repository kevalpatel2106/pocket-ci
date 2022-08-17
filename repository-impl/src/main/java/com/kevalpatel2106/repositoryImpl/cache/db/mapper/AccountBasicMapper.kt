package com.kevalpatel2106.repositoryImpl.cache.db.mapper

import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountBasicDto

internal interface AccountBasicMapper {
    operator fun invoke(dto: AccountBasicDto): AccountBasic
}
