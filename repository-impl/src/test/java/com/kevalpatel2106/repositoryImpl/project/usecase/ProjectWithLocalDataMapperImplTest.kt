package com.kevalpatel2106.repositoryImpl.project.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.repositoryImpl.getProjectWithLocalDataDtoFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProjectWithLocalDataMapperImplTest {
    private val fixture = KFixture()
    private val subject = ProjectWithLocalDataMapperImpl()

    @Test
    fun `given project local data stored when mapped then check dto`() {
        val dto = getProjectWithLocalDataDtoFixture(fixture)

        val actual = subject(dto)

        val expected = Project(
            name = dto.project.name,
            remoteId = dto.project.remoteId,
            accountId = dto.project.accountId,
            image = dto.project.image,
            repoUrl = dto.project.repoUrl,
            isDisabled = dto.project.isDisabled,
            isPublic = dto.project.isPublic,
            owner = dto.project.owner,
            lastUpdatedAt = dto.project.lastUpdatedAt,
            isPinned = dto.localData!!.isPinned,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `given project local data not stored when mapped then check dto and project not pinned`() {
        val dto = getProjectWithLocalDataDtoFixture(fixture).copy(localData = null)

        val actual = subject(dto)

        val expected = Project(
            name = dto.project.name,
            remoteId = dto.project.remoteId,
            accountId = dto.project.accountId,
            image = dto.project.image,
            repoUrl = dto.project.repoUrl,
            isDisabled = dto.project.isDisabled,
            isPublic = dto.project.isPublic,
            owner = dto.project.owner,
            lastUpdatedAt = dto.project.lastUpdatedAt,
            isPinned = false,
        )
        assertEquals(expected, actual)
    }
}
