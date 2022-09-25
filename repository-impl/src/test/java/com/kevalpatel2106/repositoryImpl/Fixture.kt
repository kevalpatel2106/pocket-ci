package com.kevalpatel2106.repositoryImpl

import androidx.paging.PagingState
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getProjectIdFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.cache.db.accountTable.AccountBasicDto
import com.kevalpatel2106.cache.db.accountTable.AccountDto
import com.kevalpatel2106.cache.db.projectLocalDataTable.ProjectLocalDataDto
import com.kevalpatel2106.cache.db.projectTable.ProjectBasicDto
import com.kevalpatel2106.cache.db.projectTable.ProjectDto
import com.kevalpatel2106.cache.db.projectTable.ProjectWithLocalDataDto

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

internal fun getProjectBasicDtoFixture(fixture: KFixture) = ProjectBasicDto(
    remoteId = getProjectIdFixture(fixture),
    accountId = getAccountIdFixture(fixture),
    owner = fixture(),
    name = fixture(),
)

internal fun getProjectDtoFixture(fixture: KFixture) = ProjectDto(
    remoteId = getProjectIdFixture(fixture),
    accountId = getAccountIdFixture(fixture),
    owner = fixture(),
    name = fixture(),
    savedAt = fixture(),
    image = getUrlFixture(fixture),
    isDisabled = fixture(),
    isPublic = fixture(),
    lastUpdatedAt = fixture(),
    repoUrl = getUrlFixture(fixture),
)

internal fun getProjectLocalDataDtoFixture(fixture: KFixture) = ProjectLocalDataDto(
    remoteId = getProjectIdFixture(fixture),
    accountId = getAccountIdFixture(fixture),
    isPinned = fixture(),
)

internal fun getProjectWithLocalDataDtoFixture(fixture: KFixture) = ProjectWithLocalDataDto(
    project = getProjectDtoFixture(fixture),
    localData = getProjectLocalDataDtoFixture(fixture),
)

internal fun <R : Any, T : Any> getPagingStateFixture(fixture: KFixture) = PagingState<R, T>(
    pages = listOf(),
    anchorPosition = fixture(),
    config = fixture(),
    leadingPlaceholderCount = fixture(),
)
