package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.entity.ArtifactType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ArtifactTypeMapperImplTest {
    private val subject = ArtifactTypeMapperImpl()

    @ParameterizedTest(name = "given file name {0} when mapper invoked then check artifact type is {1}")
    @MethodSource("provideValues")
    fun `given file name when mapper invoked then check artifact type`(
        fileName: String,
        expectedType: ArtifactType,
    ) {
        assertEquals(expectedType, subject(fileName))
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments("deploy.zip", ArtifactType.ZIP),
            arguments("DEPLOY.ZIP", ArtifactType.ZIP),
            arguments("android.apk", ArtifactType.ANDROID_APK),
            arguments("ANDROID.APK", ArtifactType.ANDROID_APK),
            arguments("android.aab", ArtifactType.ANDROID_APK),
            arguments("ANDROID.aab", ArtifactType.ANDROID_APK),
            arguments("plain.txt", ArtifactType.TXT),
            arguments("PLAIN.TXT", ArtifactType.TXT),
            arguments("noextention", ArtifactType.UNKNOWN),
            arguments("unknown.exe", ArtifactType.UNKNOWN),
            arguments("unknown:aab", ArtifactType.UNKNOWN),
            arguments("", ArtifactType.UNKNOWN),
            arguments("      ", ArtifactType.UNKNOWN),
            arguments("      .apk", ArtifactType.ANDROID_APK),
            arguments(".apk", ArtifactType.ANDROID_APK),
        )
    }
}
