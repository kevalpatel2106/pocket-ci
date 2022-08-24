package com.kevalpatel2106.connector.github.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.dto.ArtifactDto
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.ArtifactType
import com.kevalpatel2106.entity.id.toArtifactId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.Date

internal class ArtifactListItemMapperImplTest {
    private val fixture = KFixture()
    private val date = fixture<Date>()
    private val artifactTypeMapper = mock<ArtifactTypeMapper> {
        on { invoke(any()) } doReturn ArtifactType.UNKNOWN
    }
    private val isoDateMapper = mock<IsoDateMapper> {
        on { invoke(any()) } doReturn date
    }
    private val subject = ArtifactListItemMapperImpl(isoDateMapper, artifactTypeMapper)

    @Test
    fun `given artifact dto with no avatar when mapped then verify artifact entity`() {
        val dto = fixture<ArtifactDto>()
        val buildId = getBuildIdFixture(fixture)

        val actual = subject(dto, buildId)

        val expected = Artifact(
            id = dto.id.toString().toArtifactId(),
            name = dto.name,
            type = ArtifactType.UNKNOWN,
            sizeBytes = dto.sizeInBytes,
            createdAt = date,
            buildId = buildId,
        )
        assertEquals(actual, expected)
    }
}
