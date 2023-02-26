package com.kevalpatel2106.feature.artifact.usecase

import com.kevalpatel2106.entity.ArtifactType
import com.kevalpatel2106.feature.artifact.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ArtifactIconMapperImplTest {
    private val subject = ArtifactIconMapperImpl()

    @ParameterizedTest(name = "given artifact type {0} when mapper invoked then check icon {1}")
    @MethodSource("provideValues")
    fun `given artifact type when mapper invoked then check icon`(
        artifactType: ArtifactType,
        expectedIcon: Int,
    ) {
        assertEquals(expectedIcon, subject(artifactType))
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(ArtifactType.ZIP, R.drawable.ic_zip_file),
            arguments(ArtifactType.ANDROID_APK, R.drawable.ic_android_apk),
            arguments(ArtifactType.TXT, R.drawable.ic_text_file),
            arguments(ArtifactType.UNKNOWN, R.drawable.ic_file),
        )
    }
}
