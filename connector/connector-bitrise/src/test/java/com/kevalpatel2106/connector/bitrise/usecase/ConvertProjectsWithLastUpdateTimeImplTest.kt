package com.kevalpatel2106.connector.bitrise.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.connector.bitrise.network.mapper.ProjectMapper
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ConvertProjectsWithLastUpdateTimeImplTest {
    private val fixture = KFixture()
    private val projectMapper = mock<ProjectMapper>()
    private val subject = ConvertProjectsWithLastUpdateTimeImpl(projectMapper)

    @Test
    fun `given list of project dtos when mapped then verify last updated date is 1 ms less than previous item`() {
        val projectDtos = listOf<ProjectDto>(fixture(), fixture())
        val accountId = getAccountIdFixture(fixture)
        val nowMills = System.currentTimeMillis()
        val projectFixture = getProjectFixture(fixture)
        whenever(projectMapper.invoke(any(), any(), any())).thenReturn(projectFixture)

        subject(accountId, projectDtos, nowMills)

        projectDtos.forEachIndexed { index, dto ->
            val expectedLastUpdate = nowMills - (index + 1)
            verify(projectMapper).invoke(
                eq(dto),
                eq(accountId),
                check { lastUpdatedAt -> assertEquals(expectedLastUpdate, lastUpdatedAt.time) },
            )
        }
    }
}
