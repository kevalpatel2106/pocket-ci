package com.kevalpatel2106.repositoryImpl.project.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.repositoryImpl.getProjectBasicDtoFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProjectBasicMapperImplTest {
    private val fixture = KFixture()
    private val subject = ProjectBasicMapperImpl()

    @Test
    fun `given project dto when mapped then check project mapper`() {
        val dto = getProjectBasicDtoFixture(fixture)

        val actual = subject(dto)

        val expected = ProjectBasic(
            name = dto.name,
            remoteId = dto.remoteId,
            accountId = dto.accountId,
            owner = dto.owner,
        )
        assertEquals(expected, actual)
    }
}
