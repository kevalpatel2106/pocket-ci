package com.kevalpatel2106.connector.github.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.dto.ProjectDto
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.entity.toUrlOrNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.Date

internal class ProjectMapperImplTest {
    private val fixture = KFixture()
    private val date = fixture<Date>()
    private val isoDateMapper = mock<IsoDateMapper> {
        on { invoke(anyOrNull()) } doReturn date
    }
    private val subject = ProjectMapperImpl(isoDateMapper)

    // region overall entity check
    @Test
    fun `given project dto when mapped then verify build entity`() {
        val dto = fixture<ProjectDto>().copy(visibility = "public", updatedAt = "testdate")
        val accountId = getAccountIdFixture(fixture)

        val actual = subject(dto, accountId)

        val expected = Project(
            remoteId = dto.id.toString().toProjectId(),
            accountId = accountId,
            name = dto.name,
            owner = dto.owner.login,
            image = null,
            repoUrl = dto.htmlUrl.toUrlOrNull(),
            isDisabled = dto.disabled,
            isPublic = true,
            isFavourite = false,
            lastUpdatedAt = date,
        )
        assertEquals(actual, expected)
    }
    // endregion

    // region specific values
    @Test
    fun `given project dto when mapped then verify result image is always null`() {
        val dto = fixture<ProjectDto>().copy(visibility = "public", updatedAt = "testdate")
        val accountId = getAccountIdFixture(fixture)

        val actual = subject(dto, accountId).image

        assertNull(actual)
    }

    @Test
    fun `given project dto when mapped then verify result is not favourite`() {
        val dto = fixture<ProjectDto>().copy(visibility = "public", updatedAt = "testdate")
        val accountId = getAccountIdFixture(fixture)

        val actual = subject(dto, accountId).isFavourite

        assertFalse(actual)
    }

    @Test
    fun `given project dto with non-public visibility when mapped then verify result is not public`() {
        val dto = fixture<ProjectDto>().copy(visibility = "private")
        val accountId = getAccountIdFixture(fixture)

        val actual = subject(dto, accountId).isPublic

        assertFalse(actual)
    }
    // endregion
}
