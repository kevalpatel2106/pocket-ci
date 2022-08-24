package com.kevalpatel2106.connector.github.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.dto.PullRequestDto
import com.kevalpatel2106.entity.PullRequest
import com.kevalpatel2106.entity.id.toPullRequestId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PullRequestMapperImplTest {
    private val fixture = KFixture()
    private val subject = PullRequestMapperImpl()

    // region overall entity check
    @Test
    fun `given pull request dto when mapped then verify build entity`() {
        val dto = fixture<PullRequestDto>()

        val actual = subject(dto)

        val expected = PullRequest(
            id = dto.id.toPullRequestId(),
            number = dto.number,
            headBranch = dto.head.ref,
            baseBranch = dto.base.ref,
        )
        assertEquals(actual, expected)
    }
    // endregion
}
