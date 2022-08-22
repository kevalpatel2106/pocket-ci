package com.kevalpatel2106.repositoryImpl.project.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date

internal class ProjectDtoMapperImplTest {
    private val fixture = KFixture()
    private val subject = ProjectDtoMapperImpl()

    @Test
    fun `given project when mapped then check dto`() {
        val project = getProjectFixture(fixture)
        val now = Date()

        val actual = subject(project, now)

        val expected = ProjectDto(
            name = project.name,
            remoteId = project.remoteId,
            accountId = project.accountId,
            image = project.image,
            repoUrl = project.repoUrl,
            isDisabled = project.isDisabled,
            isPublic = project.isPublic,
            owner = project.owner,
            lastUpdatedAt = project.lastUpdatedAt,
            savedAt = now,
        )
        assertEquals(expected, actual)
    }
}
