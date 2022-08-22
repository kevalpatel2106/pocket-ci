package com.kevalpatel2106.registration.ciSelection.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getCIInfoFixture
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.CIType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class CiSelectionDiffCallbackTest {

    @ParameterizedTest(name = "given old {0} and new {1} item when checking are contents same then return {2}")
    @MethodSource("provideAreContentSame")
    fun `given old and new item when checking are contents same then return expected value`(
        oldItem: CIInfo,
        newItem: CIInfo,
        expectedAreContentSame: Boolean,
    ) {
        val actual = CiSelectionDiffCallback.areContentsTheSame(oldItem, newItem)

        Assertions.assertEquals(expectedAreContentSame, actual)
    }

    @ParameterizedTest(name = "given old {0} and new {1} item when checking are items same then return {2}")
    @MethodSource("provideAreItemSame")
    fun `given old and new item when checking are items same then return expected value`(
        oldItem: CIInfo,
        newItem: CIInfo,
        expectedAreItemSame: Boolean,
    ) {
        val actual = CiSelectionDiffCallback.areItemsTheSame(oldItem, newItem)

        Assertions.assertEquals(expectedAreItemSame, actual)
    }

    companion object {
        private val fixture = KFixture()
        private val ciInfo = getCIInfoFixture(fixture)

        @Suppress("unused")
        @JvmStatic
        fun provideAreItemSame() = listOf(
            // Format old item, new item, expected are content same
            arguments(ciInfo, ciInfo.copy(), true),
            arguments(ciInfo.copy(icon = 11), ciInfo.copy(icon = 10), true),
            arguments(
                ciInfo.copy(type = CIType.GITHUB),
                ciInfo.copy(type = CIType.BITRISE),
                false,
            ),
            arguments(ciInfo.copy(name = 1), ciInfo.copy(name = 2), false),
            arguments(
                ciInfo.copy(type = CIType.GITHUB, name = 1),
                ciInfo.copy(type = CIType.BITRISE, name = 2),
                false,
            ),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideAreContentSame() = listOf(
            // Format old item, new item, expected are item same
            arguments(ciInfo, ciInfo.copy(), true),
            arguments(ciInfo.copy(icon = 11), ciInfo.copy(icon = 10), false),
            arguments(
                ciInfo.copy(type = CIType.GITHUB),
                ciInfo.copy(type = CIType.BITRISE),
                false,
            ),
            arguments(ciInfo.copy(name = 1), ciInfo.copy(name = 2), false),
        )
    }
}
