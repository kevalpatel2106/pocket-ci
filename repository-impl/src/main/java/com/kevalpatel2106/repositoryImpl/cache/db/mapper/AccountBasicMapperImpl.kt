package com.kevalpatel2106.repositoryImpl.cache.db.mapper

import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountBasicDto
import javax.inject.Inject

internal class AccountBasicMapperImpl @Inject constructor() : AccountBasicMapper {

    override operator fun invoke(dto: AccountBasicDto) = with(dto) {
        AccountBasic(localId = id, authToken = token, baseUrl = baseUrl, type = type)
    }
}
