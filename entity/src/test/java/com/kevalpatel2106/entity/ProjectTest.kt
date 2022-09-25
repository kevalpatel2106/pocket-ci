package com.kevalpatel2106.entity

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.id.toAccountId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProjectTest {
    private val fixture = KFixture()

    @Test
    fun `given project check full name`() {
        val project = Project(
            remoteId = fixture(),
            name = fixture(),
            image = null,
            lastUpdatedAt = fixture(),
            repoUrl = null,
            isPinned = fixture(),
            isPublic = fixture(),
            isDisabled = fixture(),
            accountId = fixture.range(0L..Long.MAX_VALUE).toAccountId(),
            owner = fixture(),
        )

        val expected = "${project.owner}/${project.name}"
        assertEquals(expected, project.fullName)
    }
}
