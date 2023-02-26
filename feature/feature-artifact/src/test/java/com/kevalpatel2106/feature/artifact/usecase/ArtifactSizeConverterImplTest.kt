package com.kevalpatel2106.feature.artifact.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ArtifactSizeConverterImplTest {
    private val subject = ArtifactSizeConverterImpl()

    @ParameterizedTest(name = "given artifact size {0} bytes when converted then human readable size is {1}")
    @MethodSource("provideValues")
    fun `given artifact size bytes when converted then check human readable size`(
        inputBytes: Long,
        humanReadable: String,
    ) {
        assertEquals(humanReadable, subject(inputBytes))
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(1L, "1 B"),
            arguments(500L, "500 B"),
            arguments(1023L, "1023 B"),
            arguments(1024L, "1.00 KB"),
            arguments(1023L * 1024L, "1023.00 KB"),
            arguments(1024L * 1024L, "1.00 MB"),
            arguments((1.5 * 1024L * 1024L).toLong(), "1.50 MB"),
            arguments(199 * 1024L * 1024L, "199.00 MB"),
            arguments((1.001 * 1024L * 1024L * 1024L).toLong(), "1.00 GB"),
            arguments((1.0119 * 1024L * 1024L * 1024L).toLong(), "1.01 GB"),
            arguments((1.1 * 1024L * 1024L * 1024L).toLong(), "1.10 GB"),
            arguments((100.99 * 1024L * 1024L * 1024L).toLong(), "100.99 GB"),
        )
    }
}
