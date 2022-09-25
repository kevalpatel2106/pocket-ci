package com.kevalpatel2106.cache

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getProjectIdFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.cache.db.accountTable.AccountDto
import com.kevalpatel2106.cache.db.projectLocalDataTable.ProjectLocalDataDto
import com.kevalpatel2106.cache.db.projectTable.ProjectDto

internal fun getProjectDtoFixture(fixture: KFixture) = ProjectDto(
    remoteId = fixture(),
    name = fixture(),
    owner = fixture(),
    accountId = getAccountIdFixture(fixture),
    repoUrl = fixture(),
    image = fixture(),
    isDisabled = fixture(),
    isPublic = fixture(),
    lastUpdatedAt = fixture(),
    savedAt = fixture(),
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

internal fun getProjectLocalDataFixture(fixture: KFixture) = ProjectLocalDataDto(
    accountId = getAccountIdFixture(fixture),
    remoteId = getProjectIdFixture(fixture),
    isPinned = fixture(),
    updatedAt = fixture(),
)
