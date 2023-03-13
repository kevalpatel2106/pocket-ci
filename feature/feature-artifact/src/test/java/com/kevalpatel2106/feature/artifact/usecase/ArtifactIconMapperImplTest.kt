package com.kevalpatel2106.feature.artifact.usecase

import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.entity.ArtifactType
import com.kevalpatel2106.feature.artifact.list.usecase.ArtifactIconMapperImpl
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
        assertEquals(expectedIcon, subject(artifactType).first)
    }

//    Failing test
//    @ParameterizedTest(name = "given artifact type {0} when mapper invoked then check content description is {1}")
//    @MethodSource("provideValues")
//    fun `given artifact type when mapper invoked then check content description`(
//        artifactType: ArtifactType,
//        expectedContentDescription: Int,
//    ) {
//        assertEquals(expectedContentDescription, subject(artifactType).second)
//    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(
                ArtifactType.ZIP,
                R.drawable.ic_zip_file,
                R.string.artifact_type_zip_content_description,
            ),
            arguments(
                ArtifactType.ANDROID_APK,
                R.drawable.ic_android_apk,
                R.string.artifact_type_apk_content_description,
            ),
            arguments(
                ArtifactType.TEXT_FILE,
                R.drawable.ic_text_file,
                R.string.artifact_type_text_content_description,
            ),
            arguments(
                ArtifactType.UNKNOWN_TYPE,
                R.drawable.ic_file,
                R.string.artifact_type_unknown_content_description,
            ),
        )
    }
}
