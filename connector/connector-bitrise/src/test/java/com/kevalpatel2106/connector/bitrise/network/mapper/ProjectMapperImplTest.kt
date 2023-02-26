package com.kevalpatel2106.connector.bitrise.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.entity.toUrlOrNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.util.Date

internal class ProjectMapperImplTest {
    private val fixture = KFixture()
    private val date = fixture<Date>()
    private val subject = ProjectMapperImpl()

    // region overall entity check
    @Test
    fun `given project dto when mapped then verify build entity`() {
        val dto = fixture<ProjectDto>()
        val accountId = getAccountIdFixture(fixture)

        val actual = subject(dto, accountId, date)

        val expected = Project(
            remoteId = dto.slug.toProjectId(),
            accountId = accountId,
            name = dto.title,
            owner = dto.repoOwner,
            image = dto.avatarUrl.toUrlOrNull(),
            repoUrl = dto.repoUrl.toUrlOrNull(),
            isDisabled = dto.isDisabled,
            isPublic = dto.isPublic,
            isPinned = false,
            lastUpdatedAt = date,
        )
        assertEquals(actual, expected)
    }
    // endregion

    // region specific values
    @Test
    fun `given project dto when mapped then verify result is not favourite`() {
        val dto = fixture<ProjectDto>()
        val accountId = getAccountIdFixture(fixture)

        val actual = subject(dto, accountId, date).isPinned

        assertFalse(actual)
    }
    // endregion
}
