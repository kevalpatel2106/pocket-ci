package com.kevalpatel2106.repositoryImpl.appConfig

import com.flextrade.kfixture.KFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class AppConfigRepoImplTest {
    private val fixture = KFixture()

    @ParameterizedTest(
        name = "given version name {0} when get version code invoked then check version name returned is {0}"
    )
    @MethodSource("provideVersionNames")
    fun `given version name when get version code invoked then check version name returned matches`(
        input: String,
    ) {
        val subject = AppConfigRepoImpl(input, fixture(), fixture())

        val actual = subject.getVersionName()

        assertEquals(actual, input)
    }

    @ParameterizedTest(name = "given is debug {0} when is debug build invoked then returns {0}")
    @MethodSource("provideIsDebug")
    fun `given debug value when is debug build invoked then returned value matches`(
        input: Boolean,
    ) {
        val subject = AppConfigRepoImpl(fixture(), fixture(), input)

        val actual = subject.isDebugBuild()

        assertEquals(actual, input)
    }

    @ParameterizedTest(
        name = "given version code {0} when get version code invoked then check version code returned is {0}"
    )
    @MethodSource("provideVersionCodes")
    fun `given version code when get version code invoked then check version code returned matches`(
        input: Int,
    ) {
        val subject = AppConfigRepoImpl(fixture(), input, fixture())

        val actual = subject.getVersionCode()

        assertEquals(actual, input)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideVersionCodes() = mutableListOf(
            arguments(-1),
            arguments(1),
            arguments(0),
            arguments(Int.MAX_VALUE),
            arguments(Int.MIN_VALUE),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideVersionNames() = mutableListOf(
            arguments("1.0.0"),
            arguments("0.0.0"),
            arguments("${Int.MAX_VALUE}.${Int.MAX_VALUE}.${Int.MAX_VALUE}"),
            arguments("${Int.MIN_VALUE}.${Int.MIN_VALUE}.${Int.MIN_VALUE}"),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideIsDebug() = mutableListOf(arguments(true), arguments(false))
    }
}
