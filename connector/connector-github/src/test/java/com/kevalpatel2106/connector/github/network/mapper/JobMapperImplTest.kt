package com.kevalpatel2106.connector.github.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.dto.JobDto
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.entity.BuildStatus
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.Step
import com.kevalpatel2106.entity.id.JobId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.Date

internal class JobMapperImplTest {
    private val fixture = KFixture()
    private val date = fixture<Date>()
    private val buildStatus = BuildStatus.FAIL
    private val step = fixture<Step>()
    private val buildStatusMapper = mock<BuildStatusMapper> {
        on { invoke(anyOrNull(), anyOrNull()) } doReturn buildStatus
    }
    private val isoDateMapper = mock<IsoDateMapper> {
        on { invoke(null) } doReturn null
        on { invoke(any()) } doReturn date
    }
    private val stepMapper = mock<StepMapper> {
        on { invoke(anyOrNull(), anyOrNull()) } doReturn step
    }
    private val subject = JobMapperImpl(
        isoDateMapper,
        stepMapper,
        buildStatusMapper,
    )

    // region overall entity check
    @Test
    fun `given job dto with completed date when mapped then verify job`() {
        val dto = fixture<JobDto>().copy(
            steps = listOf(fixture(), fixture()),
            completedAt = "testdate",
        )
        val buildId = getBuildIdFixture(fixture)

        val actual = subject(dto, buildId)

        val expected = Job(
            id = JobId(dto.id.toString()),
            name = dto.name,
            buildId = buildId,
            triggeredAt = date,
            finishedAt = date,
            status = buildStatus,
            steps = listOf(step, step),
        )
        assertEquals(actual, expected)
    }

    @Test
    fun `given job dto with no completed date when mapped then verify job`() {
        val dto = fixture<JobDto>().copy(
            steps = listOf(fixture(), fixture()),
            completedAt = null,
        )
        val buildId = getBuildIdFixture(fixture)

        val actual = subject(dto, buildId)

        val expected = Job(
            id = JobId(dto.id.toString()),
            name = dto.name,
            buildId = buildId,
            triggeredAt = date,
            finishedAt = null,
            status = buildStatus,
            steps = listOf(step, step),
        )
        assertEquals(actual, expected)
    }
    // endregion
}
