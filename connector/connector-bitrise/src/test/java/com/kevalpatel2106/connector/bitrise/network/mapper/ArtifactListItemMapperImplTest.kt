package com.kevalpatel2106.connector.bitrise.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.dto.ArtifactListItemDto
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.ArtifactType
import com.kevalpatel2106.entity.id.toArtifactId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class ArtifactListItemMapperImplTest {
    private val fixture = KFixture()
    private val artifactTypeMapper = mock<ArtifactTypeMapper> {
        on { invoke(any()) } doReturn ArtifactType.UNKNOWN_TYPE
    }
    private val subject = ArtifactListItemMapperImpl(artifactTypeMapper)

    @Test
    fun `given artifact dto with no avatar when mapped then verify artifact entity`() {
        val dto = fixture<ArtifactListItemDto>()
        val buildId = getBuildIdFixture(fixture)

        val actual = subject(dto, buildId)

        val expected = Artifact(
            id = dto.slug.toArtifactId(),
            name = dto.title,
            type = ArtifactType.UNKNOWN_TYPE,
            sizeBytes = dto.fileSizeBytes,
            createdAt = null,
            buildId = buildId,
        )
        assertEquals(actual, expected)
    }
}
