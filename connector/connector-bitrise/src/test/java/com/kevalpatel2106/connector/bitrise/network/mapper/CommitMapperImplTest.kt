package com.kevalpatel2106.connector.bitrise.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Commit
import com.kevalpatel2106.entity.id.CommitHash
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class CommitMapperImplTest {
    private val fixture = KFixture()
    private val subject = CommitMapperImpl()

    @Test
    fun `given commit hash null when mapped then result is null`() {
        val dto = fixture<BuildDto>().copy(commitHash = null)

        val actual = subject(dto)

        assertNull(actual)
    }

    @Test
    fun `given commit hash blank when mapped then result is null`() {
        val dto = fixture<BuildDto>().copy(commitHash = "          ")

        val actual = subject(dto)

        assertNull(actual)
    }

    @Test
    fun `given commit hash empty when mapped then result is null`() {
        val dto = fixture<BuildDto>().copy(commitHash = "")

        val actual = subject(dto)

        assertNull(actual)
    }

    @Test
    fun `given commit hash not null when mapped then result is null`() {
        val commitViewUrl = getUrlFixture(fixture)
        val dto = fixture<BuildDto>().copy(
            commitHash = "1234567890",
            commitMessage = fixture(),
            commitViewUrl = commitViewUrl.value,
        )

        val actual = subject(dto)

        val expected = Commit(
            hash = CommitHash(dto.commitHash!!),
            message = dto.commitMessage,
            commitViewUrl = commitViewUrl,
            author = null,
            commitAt = null,
        )
        assertEquals(expected, actual)
    }
}
