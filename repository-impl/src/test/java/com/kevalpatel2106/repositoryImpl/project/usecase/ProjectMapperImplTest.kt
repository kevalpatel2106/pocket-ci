package com.kevalpatel2106.repositoryImpl.project.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.repositoryImpl.getProjectDtoFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProjectMapperImplTest {
    private val fixture = KFixture()
    private val subject = ProjectMapperImpl()

    @Test
    fun `given project when mapped then check dto`() {
        val dto = getProjectDtoFixture(fixture)

        val actual = subject(dto)

        val expected = Project(
            name = dto.name,
            remoteId = dto.remoteId,
            accountId = dto.accountId,
            image = dto.image,
            repoUrl = dto.repoUrl,
            isDisabled = dto.isDisabled,
            isPublic = dto.isPublic,
            owner = dto.owner,
            lastUpdatedAt = dto.lastUpdatedAt,
            isFavourite = false,
        )
        assertEquals(expected, actual)
    }
}
