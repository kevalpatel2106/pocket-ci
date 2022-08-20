package com.kevalpatel2106.repositoryImpl

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountBasicDto
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDto

internal fun getAccountBasicDtoFixture(fixture: KFixture) = AccountBasicDto(
    id = getAccountIdFixture(fixture),
    type = fixture(),
    baseUrl = getUrlFixture(fixture),
    token = fixture(),
)

internal fun getAccountDtoFixture(fixture: KFixture) = AccountDto(
    id = getAccountIdFixture(fixture),
    type = fixture(),
    baseUrl = getUrlFixture(fixture),
    token = fixture(),
    avatar = getUrlFixture(fixture),
    email = fixture(),
    name = fixture(),
    nextProjectCursor = fixture(),
    savedAt = fixture(),
    projectCacheLastUpdated = fixture(),
)
