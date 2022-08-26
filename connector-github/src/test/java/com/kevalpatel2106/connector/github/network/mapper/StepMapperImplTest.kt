package com.kevalpatel2106.connector.github.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.dto.StepDto
import com.kevalpatel2106.coreTest.getJobIdFixture
import com.kevalpatel2106.entity.BuildStatus
import com.kevalpatel2106.entity.Step
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class StepMapperImplTest {
    private val fixture = KFixture()
    private val buildStatus = BuildStatus.FAIL
    private val buildStatusMapper = mock<BuildStatusMapper> {
        on { invoke(anyOrNull(), anyOrNull()) } doReturn buildStatus
    }
    private val subject = StepMapperImpl(buildStatusMapper)

    // region overall entity check
    @Test
    fun `given step dto when mapped then verify build entity`() {
        val dto = fixture<StepDto>()
        val jobId = getJobIdFixture(fixture)

        val actual = subject(dto, jobId)

        val expected = Step(jobId = jobId, name = dto.name, status = buildStatus)
        assertEquals(actual, expected)
    }
    // endregion
}
