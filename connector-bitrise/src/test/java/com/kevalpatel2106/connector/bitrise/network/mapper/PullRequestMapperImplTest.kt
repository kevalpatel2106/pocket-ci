package com.kevalpatel2106.connector.bitrise.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.PullRequest
import com.kevalpatel2106.entity.id.toPullRequestId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PullRequestMapperImplTest {
    private val fixture = KFixture()
    private val subject = PullRequestMapperImpl()

    // region overall entity check
    @Test
    fun `given build dto when mapped then verify build entity`() {
        val dto = fixture<BuildDto>().copy(
            pullRequestId = fixture.range(1..100L),
            pullRequestTargetBranch = fixture(),
        )

        val actual = subject(dto)

        val expected = PullRequest(
            id = dto.pullRequestId.toString().toPullRequestId(),
            number = null,
            headBranch = dto.branch,
            baseBranch = dto.pullRequestTargetBranch!!,
        )
        assertEquals(actual, expected)
    }
    // endregion

    // region property specific checks
    @Test
    fun `given build dto with null target branch when mapped then error thrown`() {
        val dto = fixture<BuildDto>().copy(
            pullRequestId = fixture.range(1..100L),
            pullRequestTargetBranch = null,
        )

        assertThrows<IllegalStateException> { subject(dto) }
    }

    @Test
    fun `given build dto when mapped then verify pr number is always null`() {
        val dto = fixture<BuildDto>().copy(
            pullRequestId = fixture.range(1..100L),
            pullRequestTargetBranch = fixture<String>(),
        )

        val actual = subject(dto)?.number

        assertNull(actual)
    }

    @Test
    fun `given pull request id null when mapped then verify result is null`() {
        val dto = fixture<BuildDto>().copy(
            pullRequestId = null,
            pullRequestTargetBranch = fixture<String>(),
        )

        val actual = subject(dto)

        assertNull(actual)
    }
    // endregion
}
