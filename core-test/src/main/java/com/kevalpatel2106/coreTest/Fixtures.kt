package com.kevalpatel2106.coreTest

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.toAccountId

fun getAccountFixture(fixture: KFixture) = Account(
    localId = fixture.range(0L..Long.MAX_VALUE).toAccountId(),
    name = fixture(),
    type = fixture(),
    baseUrl = Url("http://${fixture<String>()}.com/"),
    avatar = Url("http://${fixture<String>()}.com"),
    email = fixture(),
    isSelected = fixture(),
    authToken = fixture(),
)

fun getProjectFixture(fixture: KFixture) = Project(
    remoteId = fixture(),
    name = fixture(),
    image = Url("http://${fixture<String>()}.com/image.png"),
    lastUpdatedAt = fixture(),
    repoUrl = Url("http://${fixture<String>()}.com/"),
    isFavourite = fixture(),
    isPublic = fixture(),
    isDisabled = fixture(),
    accountId = fixture.range(0L..Long.MAX_VALUE).toAccountId(),
    owner = fixture(),
)

fun getCIInfoFixture(fixture: KFixture) = CIInfo(
    type = fixture(),
    name = fixture(),
    defaultBaseUrl = Url("http://${fixture<String>()}.com/image.png"),
    infoUrl = Url("http://${fixture<String>()}.com/"),
    authTokenExplainLink = Url("http://${fixture<String>()}.com/"),
    icon = fixture(),
    sampleAuthToken = fixture(),
    supportCustomBaseUrl = fixture(),
)
