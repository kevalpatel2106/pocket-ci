package com.kevalpatel2106.connector.bitrise.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.connector.bitrise.usecase.SanitizeTriggeredBy
import com.kevalpatel2106.coreTest.getProjectIdFixture
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.BuildStatus
import com.kevalpatel2106.entity.Commit
import com.kevalpatel2106.entity.PullRequest
import com.kevalpatel2106.entity.Workflow
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toWorkflowIdOrNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Date

internal class BuildMapperImplTest {
    private val fixture = KFixture()
    private val date = fixture<Date>()
    private val commit = fixture<Commit>()
    private val pr = fixture<PullRequest>()
    private val triggeredBy = fixture<String>()
    private val pullRequestMapper = mock<PullRequestMapper> {
        on { invoke(anyOrNull()) } doReturn pr
    }
    private val buildStatusMapper = mock<BuildStatusMapper>()
    private val isoDateMapper = mock<IsoDateMapper> {
        on { invoke(anyOrNull()) } doReturn date
    }
    private val sanitizeTriggeredBy = mock<SanitizeTriggeredBy> {
        on { invoke(anyOrNull()) } doReturn triggeredBy
    }
    private val commitMapper = mock<CommitMapper> {
        on { invoke(anyOrNull()) } doReturn commit
    }
    private val subject = BuildMapperImpl(
        buildStatusMapper,
        isoDateMapper,
        sanitizeTriggeredBy,
        commitMapper,
        pullRequestMapper,
    )

    // region overall entity check
    @Test
    fun `given build dto with status finished when mapped then verify build entity with finished date`() {
        whenever(buildStatusMapper(anyOrNull())).thenReturn(BuildStatus.FAIL)
        val dto = fixture<BuildDto>().copy(
            triggeredAt = fixture(),
            commitHash = fixture(),
            pullRequestId = fixture<Long>(),
        )
        val projectId = getProjectIdFixture(fixture)

        val actual = subject(projectId, dto)

        val expected = Build(
            id = dto.slug.toBuildId(),
            projectId = projectId,
            number = dto.buildNumber,
            finishedAt = date,
            triggeredAt = date,
            workflow = Workflow(
                dto.triggeredWorkflowId.toWorkflowIdOrNull(),
                dto.triggeredWorkflow,
            ),
            status = BuildStatus.FAIL,
            commit = commit,
            headBranch = dto.branch,
            triggeredBy = triggeredBy,
            pullRequest = pr,
            abortReason = dto.abortReason,
        )
        assertEquals(actual, expected)
    }

    @Test
    fun `given build dto with status in progress when mapped then verify build entity without finished date`() {
        whenever(buildStatusMapper(anyOrNull())).thenReturn(BuildStatus.PENDING)
        val dto = fixture<BuildDto>().copy(
            triggeredAt = fixture(),
            commitHash = fixture(),
            pullRequestId = fixture<Long>(),
        )
        val projectId = getProjectIdFixture(fixture)

        val actual = subject(projectId, dto)

        val expected = Build(
            id = dto.slug.toBuildId(),
            projectId = projectId,
            number = dto.buildNumber,
            finishedAt = null,
            triggeredAt = date,
            workflow = Workflow(
                dto.triggeredWorkflowId.toWorkflowIdOrNull(),
                dto.triggeredWorkflow,
            ),
            status = BuildStatus.PENDING,
            commit = commit,
            headBranch = dto.branch,
            triggeredBy = triggeredBy,
            pullRequest = pr,
            abortReason = dto.abortReason,
        )
        assertEquals(actual, expected)
    }
    // endregion
}
