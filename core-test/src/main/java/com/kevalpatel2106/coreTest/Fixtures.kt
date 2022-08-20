package com.kevalpatel2106.coreTest

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.toAccountId

fun getUrlFixture(kFixture: KFixture) = Url("https://${kFixture<String>()}.com/")

fun getAccountIdFixture(kFixture: KFixture) = kFixture.range(0L..Long.MAX_VALUE).toAccountId()

fun getAccountFixture(fixture: KFixture) = Account(
    localId = fixture.range(0L..Long.MAX_VALUE).toAccountId(),
    name = fixture(),
    type = fixture(),
    baseUrl = getUrlFixture(fixture),
    avatar = getUrlFixture(fixture),
    email = fixture(),
    isSelected = fixture(),
    authToken = fixture(),
)

fun getProjectFixture(fixture: KFixture) = Project(
    remoteId = fixture(),
    name = fixture(),
    image = getUrlFixture(fixture),
    lastUpdatedAt = fixture(),
    repoUrl = getUrlFixture(fixture),
    isFavourite = fixture(),
    isPublic = fixture(),
    isDisabled = fixture(),
    accountId = getAccountIdFixture(fixture),
    owner = fixture(),
)

fun getCIInfoFixture(fixture: KFixture) = CIInfo(
    type = fixture(),
    name = fixture(),
    defaultBaseUrl = getUrlFixture(fixture),
    infoUrl = getUrlFixture(fixture),
    authTokenExplainLink = getUrlFixture(fixture),
    icon = fixture(),
    sampleAuthToken = fixture(),
    supportCustomBaseUrl = fixture(),
    supportJobLogs = fixture(),
    supportBuildLogs = fixture(),
    supportJobs = fixture(),
    supportDownloadArtifacts = fixture(),
    supportViewArtifacts = fixture(),
)
