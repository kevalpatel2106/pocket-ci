@file:SuppressWarnings("TooManyFunctions", "MagicNumber")

package com.kevalpatel2106.coreTest

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.Step
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.ArtifactId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.toAccountId
import java.util.Date

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

fun getAccountBasicFixture(fixture: KFixture) = AccountBasic(
    localId = fixture.range(0L..Long.MAX_VALUE).toAccountId(),
    type = fixture(),
    baseUrl = getUrlFixture(fixture),
    authToken = fixture(),
)

fun getProjectIdFixture(kFixture: KFixture) = kFixture<ProjectId>()

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

fun getProjectBasicFixture(fixture: KFixture) = ProjectBasic(
    remoteId = fixture(),
    name = fixture(),
    accountId = getAccountIdFixture(fixture),
    owner = fixture(),
)

fun getBuildIdFixture(kFixture: KFixture) = kFixture<BuildId>()

fun getBuildFixture(fixture: KFixture) = Build(
    id = getBuildIdFixture(fixture),
    status = fixture(),
    triggeredAt = fixture(),
    finishedAt = fixture(),
    abortReason = fixture(),
    commit = fixture(),
    headBranch = fixture(),
    number = fixture(),
    projectId = getProjectIdFixture(fixture),
    pullRequest = fixture(),
    triggeredBy = fixture(),
    workflow = fixture(),
)

fun getJobIdFixture(kFixture: KFixture) = kFixture<JobId>()

fun getJobFixture(fixture: KFixture) = Job(
    id = fixture(),
    name = fixture(),
    buildId = fixture(),
    status = fixture(),
    triggeredAt = Date(System.currentTimeMillis()),
    finishedAt = Date(System.currentTimeMillis() + 60_000L),
    steps = listOf(getStepFixture(fixture), getStepFixture(fixture), getStepFixture(fixture)),
)

fun getArtifactIdFixture(kFixture: KFixture) = kFixture<ArtifactId>()

fun getArtifactFixture(fixture: KFixture) = Artifact(
    id = fixture(),
    name = fixture(),
    buildId = fixture(),
    type = fixture(),
    createdAt = fixture(),
    sizeBytes = fixture(),
)

fun getStepFixture(fixture: KFixture) = fixture<Step>()

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
