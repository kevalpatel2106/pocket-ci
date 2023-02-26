package com.kevalpatel2106.connector.github.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.dto.BuildDto
import com.kevalpatel2106.connector.github.network.dto.CommitDto
import com.kevalpatel2106.connector.github.network.dto.ProjectDto
import com.kevalpatel2106.coreTest.getProjectIdFixture
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.BuildStatus
import com.kevalpatel2106.entity.Commit
import com.kevalpatel2106.entity.PullRequest
import com.kevalpatel2106.entity.Workflow
import com.kevalpatel2106.entity.id.toBuildId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Date

internal class BuildMapperImplTest {
    private val fixture = KFixture()
    private val date = fixture<Date>()
    private val buildStatus = BuildStatus.FAIL
    private val commit = fixture<Commit>()
    private val pr = fixture<PullRequest>()
    private val buildStatusMapper = mock<BuildStatusMapper> {
        on { invoke(anyOrNull(), anyOrNull()) } doReturn buildStatus
    }
    private val isoDateMapper = mock<IsoDateMapper> {
        on { invoke(anyOrNull()) } doReturn date
    }
    private val commitMapper = mock<CommitMapper> {
        on { invoke(anyOrNull()) } doReturn commit
    }
    private val pullRequestMapper = mock<PullRequestMapper> {
        on { invoke(anyOrNull()) } doReturn pr
    }
    private val subject = BuildMapperImpl(
        buildStatusMapper,
        isoDateMapper,
        commitMapper,
        pullRequestMapper,
    )

    // region overall entity check
    @Test
    fun `given build dto with status finished when mapped then verify build entity with finished date`() {
        whenever(buildStatusMapper.invoke(anyOrNull(), anyOrNull())).thenReturn(BuildStatus.FAIL)
        val dto = fixture<BuildDto>().copy(
            runStartedAt = fixture<String>(),
            headCommit = fixture<CommitDto>(),
            repository = fixture<ProjectDto>(),
            pullRequests = listOf(fixture()),
        )
        val projectId = getProjectIdFixture(fixture)

        val actual = subject(projectId, dto)

        val expected = Build(
            id = dto.id.toString().toBuildId(),
            projectId = projectId,
            number = dto.runNumber,
            finishedAt = date,
            triggeredAt = date,
            workflow = Workflow(null, dto.name),
            status = buildStatus,
            commit = commit,
            headBranch = dto.headBranch,
            triggeredBy = dto.triggeringActor?.login ?: dto.actor.login,
            pullRequest = pr,
            abortReason = null, // Not supported in GitHub
        )
        assertEquals(actual, expected)
    }
    // endregion

    // region specific values
    @Test
    fun `given build dto with status in progress when mapped then verify result finished date null`() {
        whenever(buildStatusMapper.invoke(anyOrNull(), anyOrNull()))
            .thenReturn(BuildStatus.PENDING)
        val dto = fixture<BuildDto>()
        val projectId = getProjectIdFixture(fixture)

        val actual = subject(projectId, dto).finishedAt

        assertNull(actual)
    }

    @Test
    fun `given build dto with no head commit when mapped then verify result commit is null`() {
        val dto = fixture<BuildDto>().copy(runStartedAt = fixture<String>(), headCommit = null)
        val projectId = getProjectIdFixture(fixture)

        val actual = subject(projectId, dto).commit

        assertNull(actual)
    }

    @Test
    fun `given build dto with no prs when mapped then verify result pr is null`() {
        val dto = fixture<BuildDto>().copy(pullRequests = listOf())
        val projectId = getProjectIdFixture(fixture)

        val actual = subject(projectId, dto).pullRequest

        assertNull(actual)
    }

    @Test
    fun `given build dto when mapped then verify abort reason is always null`() {
        val dto = fixture<BuildDto>()
        val projectId = getProjectIdFixture(fixture)

        val actual = subject(projectId, dto).abortReason

        assertNull(actual)
    }
    // endregion
}
